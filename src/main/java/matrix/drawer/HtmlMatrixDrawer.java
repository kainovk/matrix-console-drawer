package matrix.drawer;

import matrix.Matrix;
import matrix.util.DrawerFormatter;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HtmlMatrixDrawer<T extends Number> implements Drawer<T> {

    private final String HTML_FILE_NAME = "src/main/resources/template/matrix.html";
    private final String BORDER_TOP = "border-top: 1px solid black;";
    private final String BORDER_BOTTOM = "border-bottom: 1px solid black;";
    private final String BORDER_LEFT = "border-left: 1px solid black;";
    private final String BORDER_RIGHT = "border-right: 1px solid black;";

    @Override
    public void drawMatrix(Matrix<T> matrix) {
        try {
            Document doc = loadDocument();
            Element matrixDiv = getOrCreateElement(doc, "matrix");
            Element table = getOrCreateTable(doc, matrixDiv, matrix.getColumnCount(), matrix.getRowCount());

            updateTableCells(matrix, table);

            saveDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawBorders(int width, int height, int dx, int dy) {
        try {
            Document doc = loadDocument();
            Element matrixDiv = getOrCreateElement(doc, "matrix");
            Element table = getOrCreateTable(doc, matrixDiv, width, height);

            addBordersToTable(table, width, height);

            saveDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideBorders(int width, int height, int dx, int dy) {
        try {
            Document doc = loadDocument();
            Element table = getTableIfExists(doc);

            if (table != null) {
                removeBordersFromTable(table, width, height);
                saveDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveCursor(int dx, int dy) {
        // TODO
    }

    private Document loadDocument() throws IOException {
        File htmlFile = new File(HTML_FILE_NAME);
        if (htmlFile.exists()) {
            return Jsoup.parse(htmlFile, "UTF-8");
        } else {
            Document doc = Document.createShell("");
            doc.outputSettings().escapeMode(Entities.EscapeMode.base);
            doc.outputSettings().prettyPrint(false);
            return doc;
        }
    }

    private void saveDocument(Document doc) throws IOException {
        File outFile = new File(HTML_FILE_NAME);
        FileUtils.writeStringToFile(outFile, doc.outerHtml(), "UTF-8");
    }

    private Element getOrCreateElement(Document doc, String id) {
        Element element = doc.select("#" + id).first();
        if (element == null) {
            element = doc.body().appendElement("div").attr("id", id);
        }
        return element;
    }

    private Element getOrCreateTable(Document doc, Element parent, int width, int height) {
        Element table = parent.select("table").first();
        if (table == null) {
            table = createEmptyTable(doc, width, height);
            parent.appendChild(table);
        }
        return table;
    }

    private void updateTableCells(Matrix<T> matrix, Element table) {
        Elements rows = table.select("tr");
        for (int i = 0; i < matrix.getRowCount(); i++) {
            Element row = rows.get(i);
            Elements cells = row.select("td");
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                Element cell = cells.get(j);
                String element = DrawerFormatter.getElementToDraw(matrix, i, j);
                cell.text(element);
            }
        }
    }

    private void addBordersToTable(Element table, int width, int height) {
        for (int j = 0; j < width; j++) {
            Element firstRowCell = table.select("tr:first-of-type td:eq(" + j + ")").first();
            if (firstRowCell != null) {
                firstRowCell.attr("style", firstRowCell.attr("style") + BORDER_TOP);
            }

            Element lastRowCell = table.select("tr:last-of-type td:eq(" + j + ")").first();
            if (lastRowCell != null) {
                lastRowCell.attr("style", lastRowCell.attr("style") + BORDER_BOTTOM);
            }
        }

        for (int i = 0; i < height; i++) {
            Element leftCell = table.select("tr:eq(" + i + ") td:first-of-type").first();
            if (leftCell != null) {
                leftCell.attr("style", leftCell.attr("style") + BORDER_LEFT);
            }

            Element rightCell = table.select("tr:eq(" + i + ") td:last-of-type").first();
            if (rightCell != null) {
                rightCell.attr("style", rightCell.attr("style") + BORDER_RIGHT);
            }
        }
    }

    private void removeBordersFromTable(Element table, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Element cell = table.select("tr:eq(" + i + ") td:eq(" + j + ")").first();
                if (cell != null) {
                    String style = cell.attr("style")
                            .replace(BORDER_TOP, "")
                            .replace(BORDER_BOTTOM, "")
                            .replace(BORDER_LEFT, "")
                            .replace(BORDER_RIGHT, "");
                    cell.attr("style", style);
                }
            }
        }
    }

    private Element getTableIfExists(Document doc) {
        Element matrixDiv = doc.select("#matrix").first();
        if (matrixDiv != null) {
            return matrixDiv.select("table").first();
        }
        return null;
    }

    private Element createEmptyTable(Document doc, int width, int height) {
        Element table = doc.createElement("table");

        for (int i = 0; i < height; i++) {
            Element row = doc.createElement("tr");
            for (int j = 0; j < width; j++) {
                Element cell = doc.createElement("td");
                cell.attr("style", "");
                row.appendChild(cell);
            }
            table.appendChild(row);
        }

        return table;
    }
}
