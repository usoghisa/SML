package sml;

/**
 * 
 * Multiply the contents of registers s1 and s2 and store the result in register r
 * 
 * @author someone
 */

public class MulInstruction extends Instruction {

	private int result;
	private int op1;
	private int op2;

	public MulInstruction(String label, String op) {
		super(label, op);
	}

	public MulInstruction(String label, int result, int op1, int op2) {
		this(label, "div");
		this.result = result;
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(op1);
		int value2 = m.getRegisters().getRegister(op2);
		//System.out.println("______Result value1 "+value1);
		//System.out.println("______Result value2 "+value2);
		m.getRegisters().setRegister(result, value1 * value2);// result name of reg wher stor n
		//System.out.println("______Result  "+m.getRegisters().getRegister(this.result));
	}

	@Override
	public String toString() {
		return super.toString() + " " + op1 + " * " + op2 + " to " + result;
	}
}
