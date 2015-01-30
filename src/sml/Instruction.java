package sml;

/**
 * This class is the superclass of the classes for machine instructions
 * 
 * @author someone
 */

public abstract class Instruction {
	protected String label;
	protected String opcode;
	//protected String outPut;
	
	// Constructor: an instruction with label l and opcode op
	// (op must be an operation of the language)

	public Instruction(String l, String op ) {
		this.label = l;
		this.opcode = op;
		//this.outPut = out;
	}

	// = the representation "label: opcode" of this Instruction

	@Override
	public String toString() {
		return label + ": " + opcode;// + ": " + opcode;
	}

	// Execute this instruction on machine m.

	public abstract void execute(Machine m);
}
