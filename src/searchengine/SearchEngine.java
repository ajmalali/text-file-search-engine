/*
 * Search Engine
 */
package searchengine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Ajmal
 */
public class SearchEngine extends javax.swing.JFrame {

    private final HashMap<String, ArrayList<Postings>> invertedIndex;
    private final String path = "Source Files/";
    private final File[] directory = new File(path).listFiles();
    private final NumberFormat formatter = new DecimalFormat("#0.00000");
    private final StyledDocument doc;
    private final Style style;

    private final List<String> stopwords = Arrays.asList("a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "about", "above", "after", "again",
            "against", "all", "am", "an", "and", "any", "are", "as", "at", "be",
            "because", "been", "before", "being", "below", "between", "both",
            "but", "by", "could", "did", "do", "does", "doing", "down", "during",
            "each", "few", "for", "from", "further", "had", "has", "have", "having",
            "he", "he", "he", "ll", "he", "her", "here", "here", "hers", "herself",
            "him", "himself", "his", "how", "ve", "if", "in", "into", "is", "its", "it",
            "itself", "let", "me", "more", "most", "my", "myself", "nor", "of", "on",
            "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out",
            "over", "own", "same", "she", "should", "so", "some", "such", "than", "that",
            "that", "the", "their", "theirs", "them", "themselves", "then", "there", "these",
            "they", "re", "ve", "this", "those", "through", "to", "too", "under", "until",
            "up", "very", "was", "we", "were", "what", "when", "where", "which", "while",
            "who", "whom", "why", "with", "would", "you", "your", "yours", "yourself",
            "yourselves", " ", "may", "can", "also", "not", "no", "will");

    /**
     * Creates new form SearchEngine
     */
    public SearchEngine() {
        initComponents();
        invertedIndex = getInvertedIndex();
        this.style = resultsPane.addStyle("Color Style", null);
        this.doc = resultsPane.getStyledDocument();
    }

    private HashMap<String, ArrayList<Postings>> getInvertedIndex() {
        long startTime = System.currentTimeMillis();
        if (directory == null) {
            System.out.println("directory not found");
            System.exit(0);
        }
        int processedDocs = directory.length;

        HashMap<String, ArrayList<Postings>> map = new HashMap<>(29131); //inverted index
        ArrayList<Postings> list;
        int totalWords;

        for (int i = 0; i < processedDocs; i++) {
            try {
                String orignalText = new String(Files.readAllBytes(Paths.get(directory[i].getPath()))); // reads all text in one go 
                // removes all non words, changes to lower case and splits it into array
                String[] processedTexts = orignalText.replaceAll("[^a-zA-Z ]", " ").trim().toLowerCase().split("\\s+"); // (\\s+ treats all continuous whitespaces as a boundary)
                String filename = directory[i].getName();
                totalWords = processedTexts.length;

                for (String word : processedTexts) {
                    word = word.trim();
                    if (stopwords.contains(word)) {
                        continue; // ignore stopwords
                    }

                    Postings postings = new Postings(1.0 / totalWords, i + 1, filename, processedTexts, totalWords);

                    list = map.get(word);                                               // get list of a word
                    if (list == null) {                                                 // if word doesn't have an associated list
                        ArrayList<Postings> newList = new ArrayList<>();                // create new list
                        newList.add(postings);
                        map.put(word, newList);
                    } else if (list.get(list.size() - 1).getDocNumber() == i + 1) {     // if in same file
                        list.get(list.size() - 1).updateRelativeFreq();                 // upadte relative frequnency of latest posting
                    } else {
                        list.add(postings);                                             // else add new postings to associated list
                        map.put(word, list);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to open file");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Unable to process file");
                System.exit(0);
            }
        }
        long timeTaken = System.currentTimeMillis();
        timeTakenLabel.setText(map.keySet().size() + " words indexed in " + (timeTaken - startTime) / 1000.0 + "s");
        return map;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sortByGroup = new javax.swing.ButtonGroup();
        searchField = new javax.swing.JTextField();
        titleLabel = new javax.swing.JLabel();
        instructionsLabel = new javax.swing.JLabel();
        timeTakenLabel = new javax.swing.JLabel();
        resultsLabel = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        printInvertedIndex = new javax.swing.JButton();
        ascRadio = new javax.swing.JRadioButton();
        dscRadio = new javax.swing.JRadioButton();
        sortByLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsPane = new JTextPane() {
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                <= getParent().getSize().width;
            }
        };
        IncDocRadio = new javax.swing.JRadioButton();
        DecDocRadio = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text File Search Engine");
        setBackground(new java.awt.Color(255, 255, 255));

        searchField.setFont(new java.awt.Font("Lucida Grande", 0, 25)); // NOI18N
        searchField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchField.setActionCommand("<Not Set>");

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Text Field Search Engine");

        instructionsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        instructionsLabel.setText("Enter a word or enter multiple words with AND, NOT and OR");

        timeTakenLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        timeTakenLabel.setText("Time taken to index ____ words: ____s");

        resultsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        resultsLabel.setText(" ");

        searchButton.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        searchButton.setText("SEARCH");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonClicked(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        clearButton.setText("CLEAR");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonClicked(evt);
            }
        });

        printInvertedIndex.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        printInvertedIndex.setText("Print Inverted Index");
        printInvertedIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printInvertedIndexButtonClicked(evt);
            }
        });

        sortByGroup.add(ascRadio);
        ascRadio.setText("Lowest R. frequency");

        sortByGroup.add(dscRadio);
        dscRadio.setSelected(true);
        dscRadio.setText("Highest R. frequency");

        sortByLabel.setFont(new java.awt.Font("Lucida Grande", 0, 20)); // NOI18N
        sortByLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sortByLabel.setText("Sort results by");

        resultsPane.setEditable(false);
        resultsPane.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        resultsPane.setMargin(new java.awt.Insets(20, 20, 20, 20));
        jScrollPane1.setViewportView(resultsPane);

        sortByGroup.add(IncDocRadio);
        IncDocRadio.setText("Increasing Doc. Number");
        IncDocRadio.setToolTipText("");

        sortByGroup.add(DecDocRadio);
        DecDocRadio.setText("Decreasing Doc. Number");
        DecDocRadio.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(254, 254, 254)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dscRadio)
                                    .addComponent(ascRadio)
                                    .addComponent(IncDocRadio)
                                    .addComponent(DecDocRadio)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(resultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeTakenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(printInvertedIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(instructionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sortByLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(timeTakenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printInvertedIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sortByLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(instructionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IncDocRadio))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(DecDocRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dscRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ascRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(resultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jScrollPane1.getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonClicked
        if (!resultsPane.getText().equals("")) {
            resultsPane.setText("");
        }
        if (searchField.getText().equals("") || searchField.getText().equals("Enter a word here")) {
            searchField.setText("Enter a word here");
        } else {
            String searchEntry = searchField.getText().toLowerCase();
            searchEntry = searchEntry.trim();

            if (stopwords.contains(searchEntry)) {
                displayError("\"" + searchEntry + "\" is a stopword");
            } else {
                if (searchEntry.contains(" and ")) {
                    andQuery(searchEntry.split(" and "), searchEntry);
                } else if (searchEntry.contains(" or ")) {
                    orQuery(searchEntry.split(" or "), searchEntry);
                } else if (searchEntry.contains("not ")) {
                    String wordArray[] = searchEntry.split("not ");
                    notQuery(wordArray, searchEntry);
                } else if (!searchEntry.contains(" ")) {
                    singleTermQuery(searchEntry);
                } else {
                    displayError("Invalid search entry!");
                }
            }

            resultsPane.setCaretPosition(0); // brings to top
        }
    }//GEN-LAST:event_searchButtonClicked

    private void singleTermQuery(String searchEntry) {
        if (isIndexed(searchEntry)) {
            ArrayList<Postings> postingsList = new ArrayList<>(invertedIndex.get(searchEntry));
            String[] wordArray = {searchEntry};
            printResults(postingsList, wordArray, searchEntry);
        } else {
            displayError("\"" + searchEntry + "\" was not found!");
        }
    }

    private void andQuery(String[] inputs, String searchEntry) {
        if (!isIndexed(inputs[0])) {
            displayError("\"" + inputs[0] + "\" was not found!");
            return;
        }
        if (!isIndexed(inputs[1])) {
            displayError("\"" + inputs[1] + "\" was not found!");
            return;
        }

        ArrayList<Postings> mainList = new ArrayList<>(invertedIndex.get(inputs[0]));
        for (int i = 1; i < inputs.length; i++) {
            if (!isIndexed(inputs[i])) {
                displayError("\"" + inputs[i] + "\" was not found!");
                return;
            }
            ArrayList<Postings> otherLists = new ArrayList<>(invertedIndex.get(inputs[i]));
            mainList.retainAll(otherLists);

            for (ListIterator<Postings> it = mainList.listIterator(); it.hasNext();) {
                Postings p1 = it.next();
                for (Postings p2 : otherLists) {
                    if (p1.equals(p2)) {
                        it.set(new Postings(p1.getRelativeFreq() * p2.getRelativeFreq(),
                                p1.getDocNumber(), p1.getFileName(), p1.getAllText(), p1.getTotalWords()));
                    }
                }
            }

        }
        printResults(mainList, inputs, searchEntry);
    }

    private void orQuery(String[] inputs, String searchEntry) {
        if (!isIndexed(inputs[0])) {
            displayError("\"" + inputs[0] + "\" was not found!");
            return;
        }
        if (!isIndexed(inputs[1])) {
            displayError("\"" + inputs[1] + "\" was not found!");
            return;
        }

        Set<Postings> set = new HashSet<>();
        ArrayList<Postings> commonList = new ArrayList<>(invertedIndex.get(inputs[0]));
        set.addAll(new HashSet<>(commonList));
        for (int i = 1; i < inputs.length; i++) {
            if (!isIndexed(inputs[i])) {
                displayError("\"" + inputs[i] + "\" was not found!");
                return;
            }
            // similar to AND query
            ArrayList<Postings> otherLists = new ArrayList<>(invertedIndex.get(inputs[i]));
            set.addAll(new HashSet<>(otherLists));
            commonList.retainAll(otherLists);

            for (ListIterator<Postings> it = commonList.listIterator(); it.hasNext();) {
                Postings p1 = it.next();
                for (Postings p2 : otherLists) {
                    if (p1.equals(p2)) {
                        it.set(new Postings(p1.getRelativeFreq() + p2.getRelativeFreq(),
                                p1.getDocNumber(), p1.getFileName(), p1.getAllText(), p1.getTotalWords()));
                    }
                }
            }
        }

        for (Postings p : commonList) {
            set.remove(p);
            set.add(p);
        }
        printResults(new ArrayList<>(set), inputs, searchEntry);
    }

    private void notQuery(String[] inputs, String searchEntry) {
        // make arraylist of sources' names
        ArrayList<String> sources = new ArrayList<>();
        for (File file : directory) {
            sources.add(file.getName());
        }

        // remove all sources with input sources
        for (int i = 1; i < inputs.length; i++) {
            if (isIndexed(inputs[i].trim())) {
                inputs[i] = inputs[i].trim();
                ArrayList<Postings> list = new ArrayList<>(invertedIndex.get(inputs[i]));
                for (Postings p : list) {
                    sources.remove(p.getFileName());
                }
            } else {
                displayError("\"" + inputs[i] + "\" was not found");
                break;
            }
            resultsLabel.setText(sources.size() + " results found for the search entry \"" + searchEntry + "\"");
            resultsPane.setText("");
            int sno = 0;
            for (String fileName : sources) {
                addToPane(++sno + ".  ", Color.BLACK);
                addSourceLink(fileName);
            }
        }
    }

    private void printResults(ArrayList<Postings> list, String[] inputs, String searchEntry) {
        sortList(list);
        resultsLabel.setText(list.size() + " results found for " + "\"" + searchEntry + "\"");
        int sno = 0;
        for (Postings p : list) {
            addToPane(++sno + ".  ", Color.BLACK);                       //Serial number
            String[] allText = p.getAllText();
            for (int i = 0; i < 10; i++) {
                addToPane(allText[i].toUpperCase() + " ", Color.BLACK); //Title
            }
            addToPane(" ... ", Color.BLACK);
            addToPane("\n\n\t", Color.BLACK);

            for (String searchWord : inputs) {
                int indexOfWord = Arrays.asList(allText).indexOf(searchWord);
                int limit = indexOfWord + 5;

                if (indexOfWord < 0) {
                    continue;
                } else {
                    indexOfWord -= 5;
                }
                for (int i = indexOfWord; i <= limit; i++) {
                    try {
                        if (allText[i].equals(searchWord)) {
                            addToPane(allText[i].toUpperCase() + " ", Color.BLUE); //Search Text
                        } else {
                            addToPane(allText[i] + " ", Color.BLACK); //Text
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
                addToPane("\n\t", Color.BLACK);
            }
            addToPane("\n\tRelative frequency: " + formatter.format(p.getRelativeFreq() * 10000) + "\n\t", Color.BLACK);
            addSourceLink(p.getFileName());
        }
    }

    public void addToPane(String text, Color color) {
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
        }
    }

    private void addSourceLink(String fileName) {
        JLabel sourceLink = new JLabel("<html><u>Source: " + fileName + "</u></html>"); // Underlined Source
        sourceLink.setFont(resultsPane.getFont());
        sourceLink.setForeground(Color.BLUE);
        sourceLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sourceLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                try {
                    Desktop.getDesktop().open(new File(path + fileName));
                } catch (IOException ex) {
                    System.out.println("unable to find " + fileName);
                    Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        resultsPane.insertComponent(sourceLink);
        addToPane("\n\n\n", Color.BLACK);
    }

    private void sortList(ArrayList<Postings> list) {
        if (ascRadio.isSelected()) {
            Collections.sort(list, (Postings o1, Postings o2) -> {
                if (o2.getRelativeFreq() - o1.getRelativeFreq() > 0) {
                    return -1;
                } else if (o2.getRelativeFreq() - o1.getRelativeFreq() < 0) {
                    return 1;
                }
                return 0;
            });
        } else if (IncDocRadio.isSelected()) {
            Collections.sort(list, (Postings o1, Postings o2) -> {
                if (o2.getFileName().compareToIgnoreCase(o1.getFileName()) > 0) {
                    return -1;
                } else if (o2.getFileName().compareToIgnoreCase(o1.getFileName()) < 0) {
                    return 1;
                }
                return 0;
            });
        } else if (DecDocRadio.isSelected()) {
            Collections.sort(list, (Postings o1, Postings o2) -> {
                if (o2.getFileName().compareToIgnoreCase(o1.getFileName()) < 0) {
                    return -1;
                } else if (o2.getFileName().compareToIgnoreCase(o1.getFileName()) > 0) {
                    return 1;
                }
                return 0;
            });
        } else {
            Collections.sort(list); // default is descending order. Defined in class.
        }
    }

    private boolean isIndexed(String word) {
        return invertedIndex.containsKey(word);
    }

    private void displayError(String message) {
        resultsLabel.setText("");
        resultsPane.setText("");
        resultsLabel.setText(message);
    }

    private void clearButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonClicked
        searchField.setText("");
        resultsPane.setText("");
        resultsLabel.setText("");
    }//GEN-LAST:event_clearButtonClicked

    private void printInvertedIndexButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printInvertedIndexButtonClicked
        long startTime = System.currentTimeMillis();
        searchField.setText("");
        resultsPane.setText("");

        List<Map.Entry<String, ArrayList<Postings>>> list = new ArrayList<>(invertedIndex.entrySet());
        Collections.sort(list, new Comparator<Entry<String, ArrayList<Postings>>>() {
            @Override
            public int compare(Entry<String, ArrayList<Postings>> o1, Entry<String, ArrayList<Postings>> o2) {
                return o2.getValue().size() - (o1.getValue().size()) // sort based on list size
                        ;
            }
        });

        int sno = 0;
        for (Map.Entry<String, ArrayList<Postings>> e : list) {
            addToPane(++sno + ".  " + e.getKey() + " [" + e.getValue().size() + "]", Color.BLACK);
            for (Postings p : e.getValue()) {
                addToPane(" -> " + p.toString(), Color.BLACK);
            }
            addToPane("\n\n", Color.BLACK);
        }

        resultsLabel.setText("Inverted index sorted and printed in " + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        resultsPane.setCaretPosition(0); // brings back to top
    }//GEN-LAST:event_printInvertedIndexButtonClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SearchEngine SE = new SearchEngine();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SE.setLocationRelativeTo(null);
                SE.setResizable(false);
                SE.getRootPane().setDefaultButton(SE.searchButton);
                SE.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton DecDocRadio;
    private javax.swing.JRadioButton IncDocRadio;
    private javax.swing.JRadioButton ascRadio;
    private javax.swing.JButton clearButton;
    private javax.swing.JRadioButton dscRadio;
    private javax.swing.JLabel instructionsLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printInvertedIndex;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JTextPane resultsPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.ButtonGroup sortByGroup;
    private javax.swing.JLabel sortByLabel;
    private javax.swing.JLabel timeTakenLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
