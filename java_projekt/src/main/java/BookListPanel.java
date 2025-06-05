/*
 * BookListPanel.java
 * --------------------------------------------------------------------
 * Display and search view for the "Litera Book Catalog".
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * List of all books with search and filter options.
 */
public final class BookListPanel extends JPanel {

  /* ---------------------------------------------------------------
 * Constants / Colors / Dimensions
 * -------------------------------------------------------------- */
    private static final Color COLOR_BG_DARK   = new Color(8, 25, 40);
    private static final Color COLOR_BG_TOP    = new Color(52, 121, 122);
    private static final Color COLOR_MINT      = new Color(166, 221, 211);
    private static final Color COLOR_ROW_BG    = new Color(19, 41, 59);
    private static final Color COLOR_ROW_TEXT  = new Color(173, 199, 208);

    private static final Dimension TOP_SIZE    = new Dimension(1440, 160);
    private static final Dimension FILTER_SIZE = new Dimension(180, 40);
    private static final Dimension SEARCH_SIZE = new Dimension(550, 40);
    private static final Dimension BTN_SIZE    = new Dimension(100, 40);
    private static final Dimension ROW_SIZE    = new Dimension(980, 90);
/* ---------------------------------------------------------------
 * References
 * -------------------------------------------------------------- */
    private final ApplicationInterface controller;
    private final CardLayout           navigator;
    private final JPanel               parent;

 /* ---------------------------------------------------------------
 * UI fields
 * -------------------------------------------------------------- */
    private JComboBox<String> filterBox;
    private JTextField        searchField;
    private JPanel            resultsPanel;

/* ---------------------------------------------------------------
 * Constructor
 * -------------------------------------------------------------- */
    public BookListPanel(CardLayout navigator, JPanel parent,
            ApplicationInterface controller) {

        super(new BorderLayout());
        this.controller = controller;
        this.navigator  = navigator;
        this.parent     = parent;

        setBackground(COLOR_BG_DARK);

        buildHeader();
        buildResultArea();

        print(controller.buchsuche(""));  // Start: alle Bücher
    }

  /* ---------------------------------------------------------------
 * Header: Logo • Filter • Search
 * -------------------------------------------------------------- */
    private void buildHeader() {
      
/* Top bar ---------------------------------------------------- */
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        topBar.setPreferredSize(TOP_SIZE);
        topBar.setBackground(COLOR_BG_TOP);
/* Logo ------------------------------------------------------- */
        ImageIcon logoRaw = new ImageIcon(getClass().getClassLoader().getResource("images/LogoText.png"));
        Image     logoImg = logoRaw.getImage()
                                   .getScaledInstance(110, 120, Image.SCALE_SMOOTH);
        topBar.add(new JLabel(new ImageIcon(logoImg)));

/* Filter drop-down ------------------------------------------ */

        filterBox = new JComboBox<>(new String[] {
            "Title", "Author", "Publisher", "Genre"
        });
        filterBox.setBackground(COLOR_MINT);
        filterBox.setFont(new Font("Arial", Font.PLAIN, 18));
        filterBox.setPreferredSize(FILTER_SIZE);
        filterBox.setFocusable(false);
        filterBox.setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));
        topBar.add(filterBox);

/* Search field --------------------------------------------------- */
        searchField = new JTextField();
        searchField.setPreferredSize(SEARCH_SIZE);
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        topBar.add(searchField);
/* Search button ------------------------------------------------ */

        JButton btnSearch = new JButton("Search");
        btnSearch.setPreferredSize(BTN_SIZE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 16));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setOpaque(false);
        topBar.add(btnSearch);

/* Common search action ----------------------------------------- */

        ActionListener searchAction = evt -> refresh();
        btnSearch.addActionListener(searchAction);
        searchField.addActionListener(searchAction);

      /* Heading “Books:” --------------------------------------- */

        JLabel booksLbl = new JLabel("Books:");
        booksLbl.setFont(new Font("Arial", Font.BOLD, 26));
        booksLbl.setForeground(Color.WHITE);
        booksLbl.setBorder(BorderFactory.createEmptyBorder(18, 0, 12, 0));

      /* Header container ------------------------------------------ */
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(COLOR_BG_DARK);
        header.add(topBar);
        header.add(booksLbl);

        add(header, BorderLayout.NORTH);
    }

    /* ---------------------------------------------------------------
 * Scrollable result area
 * -------------------------------------------------------------- */

    private void buildResultArea() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(COLOR_BG_DARK);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 0));

        JScrollPane scroll = new JScrollPane(
            resultsPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);

       /* Adjust scroll speed */
        JScrollBar vBar = scroll.getVerticalScrollBar();
        vBar.setUnitIncrement(35);   
        vBar.setBlockIncrement(250); 

        add(scroll, BorderLayout.CENTER);
    }

  /* ---------------------------------------------------------------
 * Execute search
 * -------------------------------------------------------------- */


    private void refresh() {
        String query    = searchField.getText().trim();
        String category = (String) filterBox.getSelectedItem();

        List<Buch> list;
        switch (category) {
            case "Author":
                list = controller.sucheNachAutor(query);
                break;
            case "Publisher":
                list = controller.sucheNachVerlag(query);
                break;
            case "Genre":
                list = controller.sucheNachGenre(query);
                break;
            default:
                list = controller.buchsuche(query);
                break;
        }

        print(list);
    }

/* ---------------------------------------------------------------
 * Display result list
 * -------------------------------------------------------------- */

    private void print(List<Buch> books) {
        resultsPanel.removeAll();

        if (books.isEmpty()) {
            JLabel none = new JLabel("Nothing found");
            none.setForeground(Color.LIGHT_GRAY);
            none.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
            resultsPanel.add(none);
        }

        for (Buch b : books) {
            resultsPanel.add(createRow(b));
            resultsPanel.add(Box.createVerticalStrut(25));
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

   
/* ---------------------------------------------------------------
 * Create individual book row
 * -------------------------------------------------------------- */

    private JPanel createRow(Buch book) {
        JPanel row = new JPanel(new BorderLayout());
        row.setPreferredSize(ROW_SIZE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_SIZE.height));
        row.setBackground(COLOR_ROW_BG);
        row.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel titleLbl = new JLabel(book.getTitle());
        titleLbl.setFont(new Font("Arial", Font.BOLD, 24));
        titleLbl.setForeground(Color.WHITE);

        JLabel authorLbl = new JLabel(book.getAuthor());
        authorLbl.setForeground(COLOR_ROW_TEXT);
        authorLbl.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(titleLbl);
        text.add(authorLbl);

        row.add(text, BorderLayout.CENTER);

       /* Click → Show book details ---------------------------------- */
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                for (Component comp : parent.getComponents()) {
                    if (comp instanceof BookDetails) {
                        ((BookDetails) comp).showBook(book);
                        break;
                    }
                }
                navigator.show(parent, "details");
            }
        });

        return row;
    }
}
