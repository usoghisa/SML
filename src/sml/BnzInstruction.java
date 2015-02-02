package sml;
/**
 * This class ....If the contents of register s1 is not zero, then make the statement labeled L2 the next one to execute
 * 
 * @author upisa01
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
			System.out.println("\t"+label +" value of register "+register+" is "+r+" continue...");
		} else {	
			System.out.println("\t"+label +" value of register "+register+" is not zero execute line "+ this.lineN);
			/*			
			System.out.println("__________"+label);
			System.out.println("__________"+m.getLabels());
			System.out.println("__________"+m.getLabels().indexOf(lineN));
			System.out.println("__________"+ m.getPc());
			System.out.println("__________"+ m.getLabels().indexOf("l12"));
			System.out.println("__________"+ m.getPc()+1);
			System.out.println("__________"+ m.getProg().get(m.getLabels().indexOf(lineN)));*/
			
			Instruction NextInstruction = (m.getProg().get(m.getLabels().indexOf(lineN)));
			Instruction ins = NextInstruction;
			ins.execute(m);		
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " register " + register + " line" + lineN;
	}
}
