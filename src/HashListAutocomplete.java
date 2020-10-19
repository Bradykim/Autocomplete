import java.util.*;

public class HashListAutocomplete implements Autocompletor
{
    private static final int MAX_PREFIX =10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete (String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        if (terms.length != weights.length) {
            throw new IllegalArgumentException("terms and weights are not the same length");
        }

        initialize(terms,weights);
    }
    public void initialize(String[] terms, double[] weights)
    {

        myMap=new HashMap<String, List<Term>>();
        for(int i =0; i< terms.length;i++)
        {
            Term t = new Term(terms[i],weights[i]);
            for(int j =0; j<MAX_PREFIX;j++)
            {
                ArrayList<Term> arr = new ArrayList<>();
                String pre ="";
                if(j<=t.getWord().length())
                {
                    pre = t.getWord().substring(0,j);
                }
                myMap.putIfAbsent(pre,arr);
                myMap.get(pre).add(t);
            }
        }
        for(String key:myMap.keySet())
        {
            Collections.sort(myMap.get(key), Comparator.comparing(Term::getWeight).reversed());
        }

    }
    public List<Term> topMatches(String prefix, int k) {

        if(myMap.containsKey(prefix))
        {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0,Math.min(k,all.size()));
            return list;
        }
        else{
            throw new RuntimeException("Specified key doesn't exist in map");
        }




    }
    public int sizeInBytes() {
        if (mySize == 0) {

            for(String key : myMap.keySet()) {
                mySize = mySize+BYTES_PER_CHAR*key.length();
                List<Term> arr = myMap.get(key);
                for(Term t : arr){
                    mySize = mySize+ t.getWord().length()* BYTES_PER_CHAR+ BYTES_PER_DOUBLE;
                }

            }
        }
        return mySize;
    }
}
