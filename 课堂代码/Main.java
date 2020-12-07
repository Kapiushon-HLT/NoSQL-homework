
public class Main {

    public static String[] reRankTwoPointers(String[] input){
        if(input == null || input.length==0){
            return new String[0];
        }
        int i = 0;
        int left = 0;
        int right = input.length-1;
        while(i<right){
            switch (input[i]) {
                case "r":
                    String t1 = input[i];
                    input[i] = input[left];
                    input[left] = t1;
                    i++;
                    left++;
                    break;
                case "g":
                    i++;
                    break;
                case "b":
                    String t2 = input[i];
                    input[i] = input[right];
                    input[right] = t2;
                    right--;

                    break;
            }
        }
        return input;
    }

    public static void main(String[] args){

    }
}
