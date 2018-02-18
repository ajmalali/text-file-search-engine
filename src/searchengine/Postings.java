
package searchengine;

/**
 *
 * @author Ajmal
 */
public class Postings implements Comparable<Postings>{
    private final int docNumber;
    private double relativeFreq;
    private final String fileName;
    private final String[] allText;
    private  final int totalWords;
    private int frequency;

    public Postings(double relativeFreq, int docNumber, String fileName, String[] allWords, int totalWords) {
        this.docNumber = docNumber;
        this.relativeFreq = relativeFreq;
        this.fileName = fileName;
        this.allText = allWords;
        this.totalWords = totalWords;
        frequency = 1;
    }

    public String[] getAllText() {
        return allText;
    }

    public void updateRelativeFreq() {
        relativeFreq = (double) (++frequency)/totalWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getDocNumber() {
        return docNumber;
    }

    public double getRelativeFreq() {
        return relativeFreq;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "[" + docNumber + ", " + relativeFreq + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Postings))
            return false;
        Postings p = (Postings) obj;
        return this.getDocNumber() == (p.getDocNumber());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.docNumber;
        return hash;
    }

    @Override
    public int compareTo(Postings o) {
        if (o.getRelativeFreq() - this.getRelativeFreq() < 0)
            return - 1;
        else if (o.getRelativeFreq() - this.getRelativeFreq() > 0)
            return 1;
        return 0;
    }
}