import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

class PrintHelloListener implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        System.out.println("Table Updated!");
    }
    //implement table listener
}