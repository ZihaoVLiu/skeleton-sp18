import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> returnMap = new HashMap<>();
        for (char c : inputSymbols) {
            if (!returnMap.containsKey(c)) {
                returnMap.put(c, 1);
            } else {
                returnMap.put(c, returnMap.get(c) + 1);
            }
        }
        return returnMap;
    }

    public static void main(String[] args) {
        char[] eightBitsSymbol = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(eightBitsSymbol);
        BinaryTrie bTrie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bTrie);
        ow.writeObject((Integer) eightBitsSymbol.length);

        Map<Character, BitSequence> lookupTable = bTrie.buildLookupTable();
        List<BitSequence> bsList = new ArrayList<>();
        for (int i = 0; i < eightBitsSymbol.length; i++) {
            bsList.add(lookupTable.get(eightBitsSymbol[i]));
        }
        BitSequence bs = BitSequence.assemble(bsList);
        ow.writeObject(bs);
    }
}
