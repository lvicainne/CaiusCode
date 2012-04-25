package caius.code;

public class MorseCode extends Code {
	private String[] morse = new String[91];
	
	public MorseCode(String name) {
		super(1, name);
		
		this.initMorse();
	}
	
	private void initMorse() {
		this.morse[48] = "-----";
		this.morse[49] = ".----";
		this.morse[50] = "..---";
		this.morse[51] = "...--";
		this.morse[52] = "....-";
		this.morse[53] = ".....";
		this.morse[54] = "-....";
		this.morse[55] = "--...";
		this.morse[56] = "---..";
		this.morse[57] = "----.";
		
		this.morse[65] = ".-";
		this.morse[66] = "-...";
		this.morse[67] = "-.-.";
		this.morse[68] = "-..";
		this.morse[69] = ".";
		this.morse[70] = "..-.";
		this.morse[71] = "--.";
		this.morse[72] = "....";
		this.morse[73] = "..";
		this.morse[74] = ".---";
		this.morse[75] = "-.-";
		this.morse[76] = ".-..";
		this.morse[77] = "--";
		this.morse[78] = "-.";
		this.morse[79] = "---";
		this.morse[80] = ".--.";
		this.morse[81] = "--.-";
		this.morse[82] = ".-.";
		this.morse[83] = "...";
		this.morse[84] = "-";
		this.morse[85] = "..-";
		this.morse[86] = "...-";
		this.morse[87] = ".--";
		this.morse[88] = "-..-";
		this.morse[89] = "-.--";
		this.morse[90] = "--..";
	}
	
	/**
	 * Convert a letter in morse code
	 * @param letter
	 * @param ti
	 * @param ta
	 * @param areaLimit
	 * @return
	 */
	protected String morseCamouflage(char currentChar) {
		String letter;
		
		if(currentChar > 90) {
			return new String("");
		} else {
			letter = this.morse[currentChar];
		}
		
		String answer = "";
		String ti = ".";
		String ta = "-";
		String separatorTiTa = "";
		boolean randomLimited = false;
		
		
		
		for(int i =0; i < letter.length(); i++) {
			char curProcessingChar = letter.charAt(i);
			

			
			//The first pass, we do not add the separator
			if(i != 1) {
				answer += separatorTiTa;
			}
			
            if(curProcessingChar == '.') {
            	answer += ti;
            } else if(curProcessingChar == '-') {
            	answer += ta;
            } else {
            	answer += curProcessingChar;
            }
			
		}
		
		return answer;
	}
	
	public String encode(String source) throws Exception {
		source = this.removeAccents(source);
		String dest = "";
		
		//Mark the passage
		boolean first = true;
		
		//Mark the end of a word
		boolean endWord = false;
		
		//Mark the end of a sentence
		boolean endSentence = false;
		
		int length = source.length();
		for(int i = 0; i < length; i++) {
			char currentChar = source.charAt(i);
			
			if(this.isLetter(currentChar)) {

				if(first) {
					first = false;
					endWord = false;
					endSentence = false;
				} else if(!endWord && !endSentence) {
					dest += this.sepChar;
				} else if(endWord && !endSentence) {
					dest += this.sepWord;
					endWord = false;
				} else if(endSentence) {
					dest += this.sepWord;
					endWord = false;
					endSentence = false;
				}

				dest += this.morseCamouflage(currentChar);

			} else {
            	if(!endWord) {
            		endWord = this.isEndWord(currentChar);
            	}
            	if(!endSentence) {
            		endSentence = this.isEndSentence(currentChar);
            	}


            }
        
		}
		
		if(dest == "") {
			throw new EmptyStringException();
		}
    
        //We add a separator on the end of the sentence
    	dest += this.sepSentence;
		return dest;
	}
	
	public String decode(String source) {
		String dest = this.removeAccents(source);
		return dest + "aaa" ;
	}

}
