package sml;

/**
 * This class ....///uuu duplicate add instru
 * label instruction register-list
 * 
 * out 22
 * @author someone
 */

public class OutInstruction extends Instruction {
	private int register;
	private int result;

	public OutInstruction(String label, String opcode) {
		super(label, opcode);
	}

	public OutInstruction(String label, int register) {	
		this(label, "out");
		this.register = register;	
	}

	@Override
	public void execute(Machine m) {
		this.result = m.getRegisters().getRegister(this.register);
		System.out.println("Result store on register "+ this.register + " is "+ this.result);
	}

	@Override
	public String toString() {
		return super.toString() + " register " + register ;
	}
	
}
