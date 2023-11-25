import java.util.*;

public class KeyGenerationTable{
    private char[] getCharArray(){
        char[] charArray = new char[37]; // 10 digits + 26 uppercase letters + 1 space

        int index = 0;

        // Add characters '0' to '9'
        for (char digit = '0'; digit <= '9'; digit++) {
            charArray[index++] = digit;
        }

        // Add characters 'A' to 'Z'
        for (char uppercaseLetter = 'A'; uppercaseLetter <= 'Z'; uppercaseLetter++) {
            charArray[index++] = uppercaseLetter;
        }

        // Add space
        charArray[index] = ' ';
        return charArray;
    }
    private HashMap<Character,Integer>[] equalWeightedCode(){
      final List<Integer> seriesList = Collections.unmodifiableList(Arrays.asList(
            1, 2, 3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 19, 21, 22, 23, 25, 26, 27,
            28, 29, 30, 35, 37, 38, 39, 41, 42, 43, 44, 45, 46, 49, 50, 51, 52, 53,
            54, 56, 57, 58, 60, 71, 75, 77, 78, 83, 85, 86, 89, 90, 92, 99, 101, 102,
            105, 106, 108, 113, 114, 116, 120, 135, 139, 141, 142, 147, 149, 150, 153,
            154, 156, 163, 165, 166, 169, 170, 172, 177, 178, 180, 184, 195, 197, 198,
            201, 202, 204, 209, 210, 212, 216, 225, 226, 228, 232, 240
    ));
        // storing characters into char array;
        char[] charArray = getCharArray();
        HashMap<Character, Integer>[] map = new HashMap[3];
        for(int i = 0;i<map.length;i++){
            map[i] = new HashMap<>();
        }
        // assigning count / charIndex var;
        int count = 0;
        int charIndex = 0;
        for(int i : seriesList){
            if(charIndex < charArray.length && count < 3){
                map[count].put(charArray[charIndex], i);
                charIndex++;
            }else if(count < 3){
                charIndex = 0;
                map[++count].put(charArray[charIndex], i);
                charIndex++;
            }
        }
        return map;
   }
    private String[][] tableValues(){
        final String[][] tValues = {
            {
                "0111110000" ,"101011010010" , "01010111000011","0001110110101001","10101010010110" , "0011001101111000", "0011110001100110"
            },
            {
                "1011101000","00101111010001","01011010111000","0001011110100110","0010101110010101","11010001110100","0100011101100101"
            },
            {
                "001111100100","110011001100","0001011110110100","01100110100101","10110010010011","0011010101110010","0100101101100011"
            },
            {
                "010111100010","00110111001010","01100110110010","01101010100011","0010110110001110","0011011001110001","0100110101011100"
            },
            {
                "011011100001","00111011001001","01101010110001","0001101110011100","0010111010001101","11100001101100","0100111001011010"
            },
            {
                "00011111011000","0000111110001101","0001101110101100","01110010011010","11000110001011","0011100101101010","0101001101011001"
            },
            {
                "100111010100","01001111000101","01110010101010","01011010011001","11001010000111","0011101001101001","0011101001101001"
            }
        };

        return tValues;
    }

    //conversion of hex to binary value
    private String hexToBinary(String hexValue) {
        int decimalValue = Integer.parseInt(hexValue, 16);

        String binaryString = Integer.toBinaryString(decimalValue);
        return String.format("%8s", binaryString).replace(' ', '0');
    }
    
    // firstly converting decimal value to hex -> binary
    private String decToHexAndConvertToBinary(int val){
    
        return hexToBinary(Integer.toHexString(val));
    }

    // converting assigned values to binary code.
    private String assignedValueToBinary(char c){
        HashMap<Character,Integer>[] map = equalWeightedCode();
        return decToHexAndConvertToBinary(map[0].get(c));
    }
    
    // converting message to binary
    public String getMessageToBinary(String message){
        message = message.toUpperCase();
        try{
            StringBuilder encodedMessage = new StringBuilder();
            for(int i = 0;i < message.length();i++){
                encodedMessage.append(assignedValueToBinary(message.charAt(i)));
            }
            return encodedMessage.toString();
        }catch(NullPointerException e){
            System.out.println("Cannot Add Special Characters");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private String convertKeyToHex(String sb){
        return "";
    }

    private String generateKey(int keyLength){
        String[][] key = tableValues();
        StringBuilder sb = new StringBuilder();
        int count = 6;
        while(sb.length() < keyLength){
            if(count < key.length){
                for(int i = 0;i<key[0].length;i++){
                    sb.append(key[count][i]);
                }
                count++;
            }
            else{
                count = 0;
            }

        }
        if(sb.length() > keyLength){
            sb.delete(keyLength, sb.length());
        }
        return sb.toString();
    }

    // dividing the message data into given block size parts
    private String[] divideDataIntoBlockSizeParts(String str, int blockSize) {
        int length = str.length();
        int partSize = (int) Math.ceil((double) length / blockSize);
        String[] parts = new String[blockSize];

        for (int i = 0; i < blockSize; i++) {
            int start = i * partSize;
            int end = Math.min((i + 1) * partSize, length);

            parts[i] = str.substring(start, end);
        }

        return parts;
    }

    private String getEncryptData(String key,String data,int blockSize){
        // first convert raw data to binary data;
        data = getMessageToBinary(data);

        // second dividing the binary data into given block size;
        if(data.length() <= blockSize || blockSize == 0){
            return "Invalid Block Size";
        }
        // storing the binary data parts into an array
        String[] dataParts = divideDataIntoBlockSizeParts(data, blockSize);
        
        for(int i = 0;i<blockSize;i++){
            System.out.println(dataParts[i]);
        }

        return "";
    }
    public static void main(String[] args) {
        KeyGenerationTable k = new KeyGenerationTable();

        k.getEncryptData("123456789", "i need 0 1 teaching assistant", 2);


    }
}