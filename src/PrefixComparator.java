import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }


    @Override
    public int compare(Term v, Term w) {
        int vlength = v.getWord().length();
        int wlength = w.getWord().length();
        String vword = v.getWord();
        String wword = w.getWord();
        int vvalue =0;
        int wvalue =0;
        for(int i = 0; i<myPrefixSize; i++)
        {
            if(vlength>i)
            {
                 vvalue = vword.charAt(i);
            }
            if(wlength>i){
                wvalue = wword.charAt(i);
            }
            if(vvalue > wvalue){
                return 1;
            }
            if(vvalue<wvalue){
                return -1;
            }
        }
        return 0;
    }

    }

