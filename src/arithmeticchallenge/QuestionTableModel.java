package arithmeticchallenge;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Class to create a TableModel to construct a JTable with Question data.
 * 
 * @author Russell McLeod
 *
 */
public class QuestionTableModel extends AbstractTableModel
{
	/**
	 * ArrayList to hold the Questions for the JTable.
	 */
	ArrayList<Question> al;

    /**
     * An Array holding the headers for each Column in the JTable.
     */
    String[] header;

    /**
     * Constructor for QuestionTableModel.
     * 
     * @param obj
     * The ArrayList containing all the Question Objects to add.
     * 
     * @param header
     * The Array containing the headers for the JTable Columns.
     */
    QuestionTableModel(ArrayList<Question> obj, String[] header)
    {
        // save the header
        this.header = header;
        // and the data
        al = obj;
    }

    /**
     * Method needed for TableModel, gets the count of Rows in Table. 
     */
    public int getRowCount()
    {
        return al.size();
    }

    /**
     * Method needed for TableModel, gets the count of Columns in Table. 
     */
    public int getColumnCount()
    {
        return header.length;
    }

    // http://stackoverflow.com/questions/32623370/how-to-set-arraylist-object-to-table-using-oop-concept
    /**
     * Method needed for TableModel, gets the value of the data in a specified Table cell.
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
    	String value = null;
    	Question row = al.get(rowIndex);

    	switch (columnIndex)
    	{
    		// If First No. Column
    		case 0:
    			value = Integer.toString(row.getFirstNo());
    			break;
    			
			// If Operator Column
    		case 1:
    			value = row.getOperator();
    			break;
    		
    		// If Second No. Column
    		case 2:
    			value = Integer.toString(row.getSecondNo());
    			break;
    			
    		// If Answer Column
    		case 3:
    			value = Integer.toString(row.getAnswer());
    			break;
    	}
        return value;
    }

    /**
     * Method needed for TableModel, gets the Name of a specified Column. 
     */
    public String getColumnName(int index)
    {
        return header[index];
    }
    
    /**
     * Method needed for TableModel, adds a specified Question Object to the TableModel's ArrayList. 
     */
    void add(Question _question)
    {
    	al.add(_question);
    	fireTableDataChanged();
    }
}