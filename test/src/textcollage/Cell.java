package textcollage;

public class Cell {
    public Cell(){
    }
    public char content;  // The character in this cell.
    public Cell next;     // Pointer to the cell to the right of this one.
    public Cell prev;     // Pointer to the cell to the left of this one.

public void moveRight() { 
           Object currentCell;
		if (currentCell.next == null) {
                Cell newCell = new Cell();
                newCell.content = ' ';
                newCell.next = null;
                newCell.prev = (Cell) currentCell;
                currentCell.next = newCell;
                currentCell = currentCell.next;
                }
        }
}