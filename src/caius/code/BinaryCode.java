package caius.code;


public class BinaryCode extends Code {
	private String[] binary = new String[91];
	
	public BinaryCode(String name) {
		super(1, name);
		
		this.initBinary();
	}
	
	private void initBinary() {

        this.binary[65] = "1";
        this.binary[66] = "10";
        this.binary[67] = "11";
        this.binary[68] = "100";
        this.binary[69] = "101";
        this.binary[70] = "110";
        this.binary[71] = "111";
        this.binary[72] = "1000";
        this.binary[73] = "1001";
        this.binary[74] = "1010";
        this.binary[75] = "1011";
        this.binary[76] = "1100";
        this.binary[77] = "1101";
        this.binary[78] = "1110";
        this.binary[79] = "1111";
        this.binary[80] = "10000";
        this.binary[81] = "10001";
        this.binary[82] = "10010";
        this.binary[83] = "10011";
        this.binary[84] = "10100";
        this.binary[85] = "10101";
        this.binary[86] = "10110";
        this.binary[87] = "10111";
        this.binary[88] = "11000";
        this.binary[89] = "11001";
        this.binary[90] = "11010";

	}
	
	@Override
	protected String encodeLetter(char currentChar) {
		String letter;
		
		if(currentChar > 90) {
			return new String("");
		} else {
			letter = this.binary[currentChar];
		}
		
		String answer = "";
		String ti = ".";
		String ta = "-";
		String separatorTiTa = "";
		
		for(int i =0; i < letter.length(); i++) {
			char curProcessingChar = letter.charAt(i);
			
			//The first pass, we do not add the separator
			if(i != 1) {
				answer += separatorTiTa;
			}
			
            if(curProcessingChar == '0') {
            	answer += ti;
            } else if(curProcessingChar == '1') {
            	answer += ta;
            } else {
            	answer += curProcessingChar;
            }
			
		}
		
		return answer;
	}

	@Override
	public String decode(String text) {
		// TODO Auto-generated method stub
		return new String("a");
	}

}
