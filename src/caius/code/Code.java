package caius.code;

abstract class Code {
	private String name;
	private int id;
	
	protected boolean hasToRemoveAccent = true;
	protected boolean keepSpecialChars = false;
	protected boolean addSeparators = true;
	
	protected String sepSentence = "///";
	protected String sepChar = "/";
	protected String sepWord = "//";
	
	public Code(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	String getName() {
		return this.name;
	}
	
	int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	
	protected String removeAccents(String chaine) {
		    if (chaine == null)
		      return null;
		 
		    String temp = "";
		    for (int i=0; i < chaine.length(); i++) {
		      if (! ((chaine.charAt(i) < 48 && chaine.charAt(i) != 32) || chaine.charAt(i) == 255 ||
		             chaine.charAt(i) == 208 || chaine.charAt(i) == 209 ||
		             chaine.charAt(i) == 215 || chaine.charAt(i) == 216 ||
		             (chaine.charAt(i) < 192 && chaine.charAt(i) > 122) ||
		             (chaine.charAt(i) < 65 && chaine.charAt(i) > 57))) {
		        temp = temp + chaine.charAt(i);
		      } else if(this.keepSpecialChars) {
		    	  temp += chaine.charAt(i);
		      }
		    }
		    temp = temp.toUpperCase();
		    temp = temp.replace('Ý', 'Y');
		    temp = temp.replaceAll("Ù | Ú | Û | Ü", "U");
		    temp = temp.replaceAll("Ò | Ó | Ô | Õ | Ö", "O");
		    temp = temp.replaceAll("Ì | Í | Î | Ï", "I");
		    temp = temp.replaceAll("È | É | Ê | Ë", "E");
		    temp = temp.replace('Ç', 'C');
		    temp = temp.replaceAll("À | Á | Â | Ã | Ä | Å | Æ", "A");
		 
		    return temp;
	}
	
	/**
	 * Determine if the char correspond to a end of word
	 * @param currentChar The char to test
	 * @return boolean, true if the char correspond to a end of word
	 */
	protected boolean isEndWord(char currentChar) {
		boolean answer = false;

		if(currentChar == ' ') {
			return true;
		} else if(currentChar == ',') {
			return true;
		} else if(currentChar == '\'') {
			return true;
		} else if(currentChar == '-') {
			return true;
		} else if(currentChar == 34) {
			return true;
		} else if(currentChar == '(') {
			return true;
		} else if(currentChar == ')') {
			return true;
		} else if(currentChar == 8) {
			return true;
		} else if(currentChar == 39) {
			return true;
		}

		return answer;
	}
	
	/**
	 * Determine if the char correspond to a end of sentence
	 * @param currentChar The char to test
	 * @return boolean, true if the char correspond to a end of sentence
	 */
	protected boolean isEndSentence(char currentChar) {
		boolean answer = false;

		if(currentChar == '.') {
			return true;
		} else if(currentChar == ':') {
			return true;
		} else if(currentChar == '!') {
			return true;
		} else if(currentChar == '?') {
			return true;
		} else if(currentChar == ';') {
			return true;
		} else if(currentChar == 10) {
			return true;
		} else if(currentChar == 13) {
			return true;
		} else if(currentChar == 33) {
			return true;
		} else if(currentChar == 58) {
			return true;
		} else if(currentChar == 59) {
			return true;
		} else if(currentChar == 63) {
			return true;
		}

		return answer;
	}
	
	
	/**
	 * Return true if the char is a letter or a number
	 * @param currentChar
	 * @return boolean
	 */
	protected boolean isLetter(char currentChar) {
		String tempString = this.removeAccents(Character.toString(currentChar));
		if(tempString.length() < 1) {
			return false;
		}
		char newChar = tempString.charAt(0);
		
		if(newChar > 64 && newChar < 91) {
			//Upper case
			return true;
		} else if(newChar > 96 && newChar < 123) {
			//Lower case
			return true;
		} else if(newChar > 47 && newChar < 58) {
			//Numbers
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * Return true if the current char is a number
	 * @param currentChar
	 * @return boolean
	 */
	protected boolean isNumber(char currentChar) {
		if(currentChar > 47 && currentChar < 58) {
			//Numbers
			return true;
		} else {
			return false;
		}

	}

	protected abstract String encodeLetter(char letter);
	
	
	public String encode(String source) throws Exception {
		if(this.hasToRemoveAccent) {
			source = this.removeAccents(source);
		}
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
					if(this.addSeparators)
						dest += this.sepChar;
				} else if(endWord && !endSentence) {
					if(this.addSeparators)
						dest += this.sepWord;
					endWord = false;
				} else if(endSentence) {
					if(this.addSeparators)
						dest += this.sepWord;
					endWord = false;
					endSentence = false;
				}

				dest += this.encodeLetter(currentChar);

			} else {
            	if(!endWord) {
            		endWord = this.isEndWord(currentChar);
            	}
            	if(!endSentence) {
            		endSentence = this.isEndSentence(currentChar);
            	}
            	if(this.keepSpecialChars) {
            		dest += Character.toString(currentChar);
            	}


            }
        
		}
		
		if(dest == "") {
			throw new EmptyStringException();
		}
    
        //We add a separator on the end of the sentence
		if(this.addSeparators)
			dest += this.sepSentence;
		return dest;
	}
	
	abstract public String decode(String text);
}
