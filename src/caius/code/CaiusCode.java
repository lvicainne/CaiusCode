package caius.code;

public class CaiusCode extends Code {
	private int shift;
	private final int NUMBER_CHARS_ALPHABET = 26;
	
	public CaiusCode(String name, int shift) {
		super(1, name);
		this.shift = shift;
		
		/**
		 * DO NOT remove special chars 
		 * (they don't make any problem with this encoding)
		 */
		this.keepSpecialChars = true;
		this.addSeparators = false;
	}
	
	private char processDecal(char letter, int shift) {
		
		shift = shift % NUMBER_CHARS_ALPHABET;
		
		if(shift < 0) {
			shift += NUMBER_CHARS_ALPHABET;
		}
		
		if(letter > 64 && letter < 91) {
			letter = (char) ((letter - 65 + shift) % NUMBER_CHARS_ALPHABET);
			letter += 65;
		} else if(letter > 96 & letter < 123) {
			letter = (char) ((letter - 97 + shift) % NUMBER_CHARS_ALPHABET);
			letter += 97;
		}
		
		return letter;
	}
	

	@Override
	protected String encodeLetter(char letter) {
		if(this.isLetter(letter) && !this.isNumber(letter)) {
			return Character.toString(this.processDecal(letter, this.shift));
		}

		return Character.toString(letter);
	}

	protected String decodeLetter(char letter) {
		if(this.isLetter(letter) && !this.isNumber(letter)) {
			return Character.toString(this.processDecal(letter, -this.shift));
		}

		return Character.toString(letter);
	}


}
