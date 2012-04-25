package caius.code;

public class CaiusCode extends Code {
	private int shift;
	
	public CaiusCode(String name, int shift) {
		super(1, name);
		this.shift = shift;
	}
	
	private char processDecal(char letter) {
		
		if(letter > 64 && letter < 91) {
			letter = (char) ((letter - 65 + this.shift) % 26);
			letter += 65;
		} else if(letter > 96 & letter < 123) {
			letter = (char) ((letter - 97 + this.shift) % 26);
			letter += 97;
		}
		
		return letter;
	}
	

	@Override
	protected String encodeLetter(char letter) {
		if(this.isLetter(letter) && !this.isNumber(letter)) {
			return Character.toString(this.processDecal(letter));
		}

		return Character.toString(letter);
	}

	@Override
	public String decode(String text) {
		// TODO Auto-generated method stub
		return text;
	}

}
