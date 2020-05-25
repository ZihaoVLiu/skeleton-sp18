import java.util.ArrayList;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
//        int maxLength = 0;
//        for (String s : asciis) {
//            if (s.length() > maxLength) {
//                maxLength = s.length();
//            }
//        }
//        int[][] arr = new int[asciis.length][];
//        for (int i = 0; i < asciis.length; i++) {
//            char[] temp = asciis[i].toCharArray();
//            for (int j = 0; j < maxLength; j++) {
//                if (temp.length <= j) {
//                    arr[i][j] = -1;
//                } else {
//                    arr[i][j] = temp[j];
//                }
//            }
//        }
        ArrayList<String> nonBlank = new ArrayList<String>();
        for (String s : asciis) {
            if (!s.trim().isEmpty()) {
                nonBlank.add(s);
            }
        }
        String[] arr = (String[]) nonBlank.toArray(new String[nonBlank.size()]);
        int W = findMax(asciis);
        int N = asciis.length;
        String[] temp = new String[asciis.length];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[256];
            for (int i = 0; i < N; i++) {
                if (d > arr[i].length() - 1) {
                    count[2]++;
                } else {
                    count[arr[i].charAt(d) + 1]++;
                }
            }
            for (int k = 1; k < 256; k++) {
                count[k] = count[k] + count[k - 1];
            }
            for (int i = 0; i < N; i++) {
                if(d > arr[i].length() - 1){
                    temp[count[1]++] = arr[i];
                }else{
                    temp[count[arr[i].charAt(d)]++] = arr[i];
                }
            }
            for (int i = 0; i < N; i++) {
                arr[i] = temp[i];
            }
        }
        return temp;
    }

    private static int findMax(String[] a) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            max = max > a[i].length() ? max : a[i].length();
        }
        return max;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(int[][] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int maxLength = asciis[0].length;
        int[] arr = new int[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            arr[i] = asciis[i][maxLength - 1 -index];
        }

        CountingSort.betterCountingSort(arr);

//        int maxLength = 0;
//        for (String s : asciis) {
//            if (s.length() > maxLength) {
//                maxLength = s.length();
//            }
//        }
//        int[] arr = new int[asciis.length];
//        for (int i = 0; i < asciis.length; i++) {
//            int ascLen = asciis[i].length();
//            arr[i] = (int) asciis[i].charAt(ascLen - 1 - index);
//        }
//        int R = 256;
//        int[] counts = new int[R];
//        for (int i : arr) {
//            counts[i]++;
//        }
//
//        int[] starts = new int[R];
//        int pos = 0;
//        for (int i = 0; i < starts.length; i += 1) {
//            starts[i] = pos;
//            pos += counts[i];
//        }
//
//        String[] dupAsciis = asciis.clone();
//        for (int i = 0; i < arr.length; i += 1) {
//            int item = arr[i];
//            int place = starts[item];
//            asciis[place] = dupAsciis[i];
//            starts[item] += 1;
//        }



    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

//    public static void main(String[] args) {
//        String[] test = new String[5];
//        test[0] = "abc";
//        test[1] = "bcd";
//        test[2] = "c";
//        test[3] = "v";
//        test[4] = "A";
//
//        sortHelperLSD(test, 0);
//        for (String s : test) {
//            System.out.println(s);
//        }
//    }
    public static void main(String[] args){
        String[] a = new String[]{ "Gotengco", "Lira","Guban", "Garraez", "Liam","Noah","William","James"
                ,"Logan","Benjamin","Mason","Elijah","Oliver","Jacob","Lucas","Michael","Alexander","Ethan"
                ,"Daniel","Matthew","Aiden","Henry","Joseph","Jackson","Samuel","Sebastian","David","Carter",
                "Wyatt","Jayden","John","Owen","Dylan","Luke","Gabriel","Anthony","Isaac","Grayson","Jack"
                ,"Julian","Levi","Christopher","Joshua","Andrew","Lincoln","Mateo","Ryan","Jaxon","Nathan",
                "Aaron","Isaiah","Thomas","Charles","Caleb","Josiah","Christian","Hunter","Eli","Jonathan",
                "Connor","Landon","Adrian","Asher","Cameron","Leo","Theodore","Jeremiah","Hudson","Robert",
                "Easton","Nolan","Nicholas","Ezra","Colton"};
        String[] b;
        b = sort(a);
        for(String s : b){
            System.out.println(s);
        }
    }

}
