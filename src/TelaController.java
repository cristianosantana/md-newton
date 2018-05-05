
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author matheus
 */
public class TelaController {

    public static char lastID = ' ';

    public void digita(char value, char id, JTextField jt) {

        switch (id) {
            case 'N':
                if (lastID == 'X') {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                } else {
                    escreve(value +"", id,jt);
                }
                break;
            case 'O':
                if (lastID == 'N' || lastID == 'X') {
                    escreve(" "+value+" ", id,jt);
                } else {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                }
                break;
            case 'P':
                if (lastID == 'N' || lastID == 'X') {
                    escreve(value +"", id, jt);
                } else {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                }
                break;
            case 'X':
                if (lastID == 'X') {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                } else {
                    escreve(value +"", id,jt);
                }
                break;
            case 'A':
                if (lastID == 'N' || lastID == 'X') {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                } else {
                    escreve(value+" ", id,jt);
                }
                break;
            case 'F':
                if (lastID == 'N' || lastID == 'X') {
                    escreve(" "+value+" ", id,jt);
                } else {
                    JOptionPane.showMessageDialog(null, "Digito invalido");
                }
                break;
        }
    }

    private void escreve(String value, char id, JTextField jt) {
        lastID = id;
        jt.setText(jt.getText() + value);
    }

}
