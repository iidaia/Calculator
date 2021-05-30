package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer str;

	Identifier(char c) {

		str = new StringBuffer();
		addChar(c);
	}

	Identifier(Identifier OldId) {

		str = new StringBuffer();
		for(int i = 0; i < OldId.getlength(); i++) {

			str.append(OldId.value().charAt(i));

		}
	}

	public void addChar(char c) {

		str.append(c);
	}

	public int getlength() {

		return str.length();
	}

	@Override
	public String value() {

		return str.toString();
	}

}
