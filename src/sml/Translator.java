package sml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";
	///////////////////
	private Properties prpt = null;
	private static final int sbConst = 1;
	private static final String strTyp = "String";	
	private static final int frstParam = 0;
	private static final String intTyp 	= "int";

	
	public Translator(String fileName) {
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}

			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();

				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}
	//////////////////////reflection using external file for future update_________________________
	public Instruction getInstruction(String label) {

		if (line.equals(""))
			return null;
	
		/*
		 * name of the class for diff instr are store in exter file In src folder named instructionsClsNam.txt
		 * so other istr clas can be generate,  put into instructionsClsNam.txt file.
		 *   
		 * create a new instance  class with the right arguments.
		 * label, s1
		 * label, r, s1
		 * label, s1, x
		 * label, r, s1, s2
		 * label x = strings s1, r, s2 = int.
		 */
		
		prpt = new Properties();
		String instrct = scan();
		Instruction instruction = null;
		Class<?> instrct_class;
		
		try {
			prpt.load(new FileInputStream("src/instructionsClsNam.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("SML properties file not found.");
		} catch (IOException e) {
			System.out.println("Java IO Exception");
		}
		//pass instr to get class name from instructionsClsNam.txt
		String instrtName = prpt.getProperty(instrct);	
		try {
			// Get the Class with the given name
			instrct_class = Class.forName(instrtName);
			// Get an array of public constructors for give class
			Constructor<?>[] constructorsArr = instrct_class.getConstructors();
			// Get the second constructor
			Constructor<?> constructor = constructorsArr[sbConst];
			// array of the constructors parameter type
			Class<?>[] prmtTyp  = constructor.getParameterTypes();
			//objects array to holding parameter values to pass to the constructor.newInstance
			Object[] parameters = new Object[prmtTyp.length];
			// first parameter is the label passed in.
			parameters[frstParam] = label;
			// Loop to check the remaining parameter types. Call scan() for String, scanInt() for int.
			for (int i = 1; i < parameters.length; i ++){
					if (prmtTyp[i].getSimpleName().equals(strTyp)){
					parameters[i] = scan();
				} else if (prmtTyp[i].getSimpleName().equals(intTyp)){
					parameters[i] = scanInt();
				}
			}
			// New instruction
			instruction = (Instruction) constructor.newInstance(parameters);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
        return instruction;
	}	
	//////////////////////// use Case_______________________________________________ 
	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	/*public Instruction getInstruction(String label) {
		int s1; // Possible operands of the instruction
		int s2;
		int r;
		int x;
		String ln;

		if (line.equals(""))
			return null;

		String ins = scan();////////////////////use  reflection here_____________________
		switch (ins) {
		case "bnz":
			r = scanInt();
			ln = scan();
			return new BnzInstruction(label, r, ln);
		case "mul":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new MulInstruction(label, r, s1, s2);
		case "div":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new DivInstruction(label, r, s1, s2);		
		case "sub":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new SubInstruction(label, r, s1, s2);
		case "add":
			r = scanInt();
			s1 = scanInt();
			s2 = scanInt();
			return new AddInstruction(label, r, s1, s2);
		case "lin":
			r = scanInt();
			s1 = scanInt();
			return new LinInstruction(label, r, s1);
		case "out":
			r = scanInt();
			//s1 = scanInt();
			//return new OutInstruction(label, r, s1);
			return new OutInstruction(label, r);
		}
		// You will have to write code here for the other instructions.
		return null;
	}*/


	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";
		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() {
		String word = scan();
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}
