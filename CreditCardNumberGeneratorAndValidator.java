import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class CreditCardNumberGeneratorAndValidator {

	public static void main(String[] args) {
		CreditCardNumberGeneratorAndValidator wlg = new CreditCardNumberGeneratorAndValidator();
		
		Scanner scan = new Scanner(System.in) ;
		String cardNum = null, opNum, tobeValidatedNum;
		
		System.out.println("Please Select an Operation:");
		System.out.println("1: Card Validation ");
		System.out.println("2: Number Probability Generator ");
		opNum = scan.next();
		
		if(opNum.equals("1")){
			System.out.println("Please enter number to be validated:  ");
			tobeValidatedNum = scan.next();
			StringBuilder newTobeValidatedNum = new StringBuilder(tobeValidatedNum);
			if(newTobeValidatedNum.length() == 16) 
			{
				
				if(calculateProbability(newTobeValidatedNum))
				{
					System.out.println("Number is validated: " + newTobeValidatedNum);
				}
				else
				{
					System.out.println("Number is invalid: " + newTobeValidatedNum);
				}
			}
			else
			{
				System.out.println("Please enter 16 digits");
				return;
			}
		}
		else if(opNum.equals("2")){
			System.out.print("Please enter number. Type x for unknown digits ");
			cardNum = scan.next();
			
			if(cardNum.length() == 16) 
			{
				List<Integer> notDigits = wlg.findNotIntegerDigits(cardNum);
				wlg.generateCombination(notDigits, cardNum);
			}
			else 
			{
				System.out.println("Please enter total 16 chars");
			}
			
		}
		else{
			System.out.print("Wrong Selection. Please select a correct operation!!!  ");
			return;
		}

		scan.close();
				
	}
	
		private List<Integer> findNotIntegerDigits(String cNumber){
		int cNumberLength=cNumber.length();
		
		List<Integer> digitList = new ArrayList<Integer>();
				
		for(int c=0; c<cNumberLength; c++){
		if (cNumber.charAt(c) == 'x'){
			digitList.add(c);
		}
		}
		return digitList;
	}
	

	private void generateCombination(List<Integer> notDigitList, String cardNum) {
		int wordlength = notDigitList.size();
		if(wordlength==0)
		{
			System.out.println("There is not unknown number");	
			return;
		}
			
		StringBuilder newCardNum = new StringBuilder(cardNum);
		char[] numList = {'0','1','2','3','4','5','6','7','8','9'};
		
		final int listLength=numList.length;
		
		final long MAX_WORDS = (long) Math.pow(listLength, wordlength);
		System.out.println("total line is: "+(MAX_WORDS/10));
	
		for (long i = 0; i < MAX_WORDS; i++) {
			int[] indices = convertTolistLength(listLength, i, wordlength);
			for (int k = 0; k < wordlength; k++) {
			newCardNum.setCharAt(notDigitList.get(k), numList[indices[k]]);
			}
			
			if(calculateProbability(newCardNum))
			{
			System.out.println("this combinasiton is correct:" + newCardNum);
			}

		}
	} 

	private static int[] convertTolistLength(int listLength, long number, int wordlength) {
		int[] indices = new int[wordlength];
		for (int x = wordlength - 1; x >= 0; x--) {
			if (number > 0) {
				int rest = (int) (number % listLength);
				number /= listLength;
				indices[x] = rest;
			} else {
				indices[x] = 0;
			}
		}
		return indices;
	}
	
	
		private static boolean calculateProbability(StringBuilder newCNum){
		int newCSize=newCNum.length();
		char eachChar;
		int sumSingle=0;
		int sumDouble=0;
		int totalSum;
		for(int ncn=0; ncn<newCSize; ncn++){
			int sumDoubleTemp=0;
			eachChar=newCNum.charAt(ncn);
			int eachDigit = Character.getNumericValue(eachChar);
			
			if(ncn %2 == 0){
				sumDoubleTemp=sumDoubleTemp+(eachDigit*2);
				if(sumDoubleTemp>=10){
					while (sumDoubleTemp != 0) {
						sumDouble += sumDoubleTemp % 10;
						sumDoubleTemp /= 10;
						}
				}
				else{
					sumDouble += sumDoubleTemp;
				}
				}
				else
				{
					sumSingle += eachDigit;
				}
				
		}
		totalSum=sumSingle+sumDouble;
		
		if(totalSum % 10 == 0)
			return true;
		else
			return false;
	} 

}