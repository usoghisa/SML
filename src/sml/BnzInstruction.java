package sml;

/**
 * This class ....If the contents of register s1 is not zero, then make the statement labeled L2 the next one to execute
 * 
 * @author someone
 */

public class BnzInstruction extends Instruction {
	private int register;
	private String lineN;

	public BnzInstruction(String label, String opcode) {
		super(label, opcode);
	}

	public BnzInstruction(String label, int register, String ln) {
		super(label, "bnz");
		this.register = register;
		this.lineN = ln;

	}

	@Override
	public void execute(Machine m) {
		int r = m.getRegisters().getRegister(this.register);
		if (r == 0) {
			System.out.println("val of register "+register+" is "+r+" continue...");
		} else {
			
			System.out.println(label +"val of register "+register+" is zero execute line "+ this.lineN);
			System.out.println("__________"+label);
			System.out.println("__________"+m.getLabels());
			System.out.println("__________"+m.getLabels().indexOf(lineN));
			System.out.println("__________"+ m.getPc());
			System.out.println("__________"+ m.getLabels().indexOf("l12"));
			System.out.println("__________"+ m.getPc()+1);
			System.out.println("__________"+ m.getProg().get(m.getLabels().indexOf(lineN)));

			m.setPc((m.getPc())+1);
			//m.setPc(m.getLabels().indexOf(lineN));		
		}

	}
	
	
	
	
	

	@Override
	public String toString() {
		return super.toString() + " register " + register + " line" + lineN;
	}
}
