
package snakepredation.jpa_Model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.skin.TextInputControlSkin;
import static javafx.scene.control.skin.TextInputControlSkin.Direction.DOWN;
import static javafx.scene.control.skin.TextInputControlSkin.Direction.LEFT;
import static javafx.scene.control.skin.TextInputControlSkin.Direction.RIGHT;
import static javafx.scene.control.skin.TextInputControlSkin.Direction.UP;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import snakepredation.Food;
import snakepredation.GameBoard;

@Entity
@Table(name = "snake")
@NamedQueries({
    @NamedQuery(name = "Snake.findAll", query = "SELECT s FROM Snake s"),
    @NamedQuery(name = "Snake.findBySnakeID", query = "SELECT s FROM Snake s WHERE s.snakeID = :snakeID"),
    @NamedQuery(name = "Snake.findBySnakeName", query = "SELECT s FROM Snake s WHERE s.snakeName = :snakeName"),
    @NamedQuery(name = "Snake.findByColorHead", query = "SELECT s FROM Snake s WHERE s.colorHead = :colorHead"),
    @NamedQuery(name = "Snake.findBySnakeSpeed", query = "SELECT s FROM Snake s WHERE s.snakeSpeed = :snakeSpeed"),
    @NamedQuery(name = "Snake.findByIsAlive", query = "SELECT s FROM Snake s WHERE s.isAlive = :isAlive"),
    @NamedQuery(name = "Snake.findByColorEyes", query = "SELECT s FROM Snake s WHERE s.colorEyes = :colorEyes")})
public class Snake implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "snakeID")
    private Collection<Player> playerCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SnakeID")
    private Integer snakeID;
    @Basic(optional = false)
    @Column(name = "SnakeName")
    private String snakeName;
    @Column(name = "ColorHead")
    private String colorHead;
    @Basic(optional = false)
    @Column(name = "SnakeSpeed")
    private int snakeSpeed;
    @Basic(optional = false)
    @Column(name = "isAlive")
    private short isAlive;
    @Column(name = "ColorEyes")
    private String colorEyes;

    private List<Point> snakeBody; // Danh sách các phần của con rắn
    private TextInputControlSkin.Direction currentDirection;// Hướng di chuyển hiện tại
    private Point headPosition; // Tọa độ của đầu con rắn
    private int scores = 0;
    private boolean canChangeDirection = true; // Biến dùng để set time out khi rắn đổi direction

    public Snake() {
    }

    public Snake(Integer snakeID) {
        this.snakeID = snakeID;
    }

    public Snake(int snakeLength, int x, int y) {
        this.snakeBody = new ArrayList<>();
        for (int i = 0; i < snakeLength; i++) {
            snakeBody.add(new Point(x, y));
        }
        this.headPosition = this.snakeBody.get(0);
        this.currentDirection = TextInputControlSkin.Direction.RIGHT;
        this.isAlive = 1; // rắn còn sống
        this.snakeSpeed = 130;
        this.scores = 0;
    }

    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(List<Point> snakeBody) {
        this.snakeBody = snakeBody;
    }

    public TextInputControlSkin.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(TextInputControlSkin.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Point getHeadPosition() {
        return headPosition;
    }

    public void setHeadPosition(Point headPosition) {
        this.headPosition = headPosition;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public Integer getSnakeID() {
        return snakeID;
    }

    public void setSnakeID(Integer snakeID) {
        this.snakeID = snakeID;
    }

    public String getSnakeName() {
        return snakeName;
    }

    public void setSnakeName(String snakeName) {
        this.snakeName = snakeName;
    }

    public String getColorHead() {
        return colorHead;
    }

    public void setColorHead(String colorHead) {
        this.colorHead = colorHead;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public void setSnakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
    }

    public short getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(short isAlive) {
        this.isAlive = isAlive;
    }

    public String getColorEyes() {
        return colorEyes;
    }

    public void setColorEyes(String colorEyes) {
        this.colorEyes = colorEyes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (snakeID != null ? snakeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snake)) {
            return false;
        }
        Snake other = (Snake) object;
        if ((this.snakeID == null && other.snakeID != null) || (this.snakeID != null && !this.snakeID.equals(other.snakeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "snakepredation.jpa_Model.Snake[ snakeID=" + snakeID + " ]";
    }

    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    public void DrawSnake(GameBoard gameboard, GraphicsContext gc, String colorHead, String colorEyes, String colorBody) {
        // Vẽ con rắn
        for (int i = 0; i < this.snakeBody.size(); i++) {
            if (i == 0) {
                // Kích thước đầu con rắn
                double headSize = gameboard.getSQUARE_SIZE() - 5;
                // Vị trí x và y cho đầu con rắn
                double headX = this.headPosition.getX() * gameboard.getSQUARE_SIZE();
                double headY = this.headPosition.getY() * gameboard.getSQUARE_SIZE();

                // Vẽ đầu con rắn 
                gc.setFill(Color.web(colorHead));
                gc.fillRoundRect(headX, headY, headSize, headSize, 25, 25);

                // Kích thước và vị trí cho mắt
                double eyeSize = 8; // Kích thước mắt
                double eyeX = headX + (headSize - eyeSize) / 2; // Vị trí x cho mắt
                double eyeY = headY + (headSize - eyeSize) / 2; // Vị trí y cho mắt

                if (this.currentDirection == TextInputControlSkin.Direction.UP || this.currentDirection == TextInputControlSkin.Direction.DOWN) {
                    // Vẽ mắt 1
                    gc.setFill(Color.web(colorEyes));
                    gc.fillRoundRect(eyeX - 5, eyeY - 2, eyeSize, eyeSize, 50, 50);
                    // Vẽ mắt 2
                    gc.setFill(Color.web(colorEyes));
                    gc.fillRoundRect(eyeX + 5, eyeY - 2, eyeSize, eyeSize, 50, 50);
                } else {
                    // Vẽ mắt 1
                    gc.setFill(Color.web(colorEyes));
                    gc.fillRoundRect(eyeX + 2, eyeY + 5, eyeSize, eyeSize, 50, 50);
                    // Vẽ mắt 2
                    gc.setFill(Color.web(colorEyes));
                    gc.fillRoundRect(eyeX + 2, eyeY - 5, eyeSize, eyeSize, 50, 50);
                }
            } else {
                int sizeRect = gameboard.getSQUARE_SIZE() - 4;
                double positionSnale_X = this.snakeBody.get(i).getX() * gameboard.getSQUARE_SIZE();
                double positionSnale_Y = this.snakeBody.get(i).getY() * gameboard.getSQUARE_SIZE();
                gc.setFill(Color.web(colorBody));
                gc.fillRect(positionSnale_X, positionSnale_Y, sizeRect, sizeRect);
            }
        }
    }

    // Handle Event của người dùng và cập nhật biến currentDirection 
    public void HandeleDirectionFor1Player(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (canChangeDirection) {
            switch (keyCode) {
                case UP:
                case W:
                    // Xử lý di chuyển lên
                    if (this.currentDirection != TextInputControlSkin.Direction.DOWN) {
                        this.currentDirection = TextInputControlSkin.Direction.UP;
                    }
                    break;
                case DOWN:
                case S:
                    // Xử lý di chuyển xuống
                    if (this.currentDirection != TextInputControlSkin.Direction.UP) {
                        this.currentDirection = TextInputControlSkin.Direction.DOWN;
                    }
                    break;
                case LEFT:
                case A:
                    // Xử lý di chuyển qua trái
                    if (this.currentDirection != TextInputControlSkin.Direction.RIGHT) {
                        this.currentDirection = TextInputControlSkin.Direction.LEFT;
                    }
                    break;
                case RIGHT:
                case D:
                    // Xử lý di chuyển qua phải
                    if (this.currentDirection != TextInputControlSkin.Direction.LEFT) {
                        this.currentDirection = TextInputControlSkin.Direction.RIGHT;
                    }
                    break;
            }
            canChangeDirection = false;
            setTimeOut();
        }
    }

    // Hàm đặt time out khi chuyển hướng rắn
    private void setTimeOut() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canChangeDirection = true;
            }
        }, 110);
    }

    // Handle Event của rắn 1 trong chế độ 2 người chơi của người dùng và cập nhật biến currentDirection
    public void HandeleDirectionFor2PlayerOfSnake2(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (canChangeDirection) {
            switch (keyCode) {
                case UP:
                    // Xử lý di chuyển lên
                    if (this.currentDirection != TextInputControlSkin.Direction.DOWN) {
                        this.currentDirection = TextInputControlSkin.Direction.UP;
                    }
                    break;
                case DOWN:
                    // Xử lý di chuyển xuống
                    if (this.currentDirection != TextInputControlSkin.Direction.UP) {
                        this.currentDirection = TextInputControlSkin.Direction.DOWN;
                    }
                    break;
                case LEFT:
                    // Xử lý di chuyển qua trái
                    if (this.currentDirection != TextInputControlSkin.Direction.RIGHT) {
                        this.currentDirection = TextInputControlSkin.Direction.LEFT;
                    }
                    break;
                case RIGHT:
                    // Xử lý di chuyển qua phải
                    if (this.currentDirection != TextInputControlSkin.Direction.LEFT) {
                        this.currentDirection = TextInputControlSkin.Direction.RIGHT;
                    }
                    break;
            }
            canChangeDirection = false;
            setTimeOut();
        }
    }
    // Handle Event của rắn 2 trong chế độ 2 người chơi của người dùng và cập nhật biến currentDirection

    public void HandeleDirectionFor2PlayerOfSnake1(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (canChangeDirection) {
            switch (keyCode) {
                case W:
                    // Xử lý di chuyển lên
                    if (this.currentDirection != TextInputControlSkin.Direction.DOWN) {
                        this.currentDirection = TextInputControlSkin.Direction.UP;
                    }
                    break;
                case S:
                    // Xử lý di chuyển xuống
                    if (this.currentDirection != TextInputControlSkin.Direction.UP) {
                        this.currentDirection = TextInputControlSkin.Direction.DOWN;
                    }
                    break;
                case A:
                    // Xử lý di chuyển qua trái
                    if (this.currentDirection != TextInputControlSkin.Direction.RIGHT) {
                        this.currentDirection = TextInputControlSkin.Direction.LEFT;
                    }
                    break;
                case D:
                    // Xử lý di chuyển qua phải
                    if (this.currentDirection != TextInputControlSkin.Direction.LEFT) {
                        this.currentDirection = TextInputControlSkin.Direction.RIGHT;
                    }
                    break;
            }
            canChangeDirection = false;
            setTimeOut();
        }
    }
    // Find and update the snake's previous position

    public void FindPreviousPosition(GraphicsContext gc, GameBoard gameboard) {
        // Tìm vị trí trước đó của con rắn  
        for (int i = this.snakeBody.size() - 1; i >= 1; i--) {
            /*
                    i-1 (vị trí trước đó) -> vị trí trước đó của con rắn
             */
            this.snakeBody.get(i).x = this.snakeBody.get(i - 1).x;
            this.snakeBody.get(i).y = this.snakeBody.get(i - 1).y;
        }
    }

    /// Handle snake move for 1 player mode
    public void HandleSnakeMove(GameBoard gameboard, Food food) {
        switch (this.currentDirection) {
            case RIGHT:
                // Xử lý khi currentDirection là RIGHT
                this.headPosition.x++;
                break;
            case LEFT:
                // Xử lý khi currentDirection là LEFT
                this.headPosition.x--;
                break;
            case UP:
                // Xử lý khi currentDirection là UP - Theo đồ họa game thì trục y thường đi xuống dưới
                this.headPosition.y--;
                break;
            case DOWN:
                // Xử lý khi currentDirection là DOWN
                this.headPosition.y++;
                break;
        }

//        // Kiểm tra còn sống không
//        if (isSnakeAlive(gameboard) == true) {
//            this.isAlive = true;
//        } else {
//            this.isAlive = false;
//        }
//
//        // Kiểm tra ran an moi chưa
//        if (isSnakeEat(food)) {
//            this.scores += 5;
//            this.snakeBody.add(new Point(-1, -1));
//            food.setExists(false);
//        } else {
//            food.setExists(true);
//        }
    }

    public boolean isSnakeAlive(GameBoard gameboard) {
        int currentWidthX = this.headPosition.x * gameboard.getSQUARE_SIZE();
        int currentHeightY = this.headPosition.y * gameboard.getSQUARE_SIZE();
//        System.out.println("cur Width:" + currentWidthX);
//        System.out.println("cur Height:" + currentHeightY);
//        System.out.println("Height:" + gameboard.getHeight());
//        System.out.println("Width:" + gameboard.getWidth());
//        System.out.println("Head X:" + this.headPosition.getX());
//        System.out.println("Head y:" + this.headPosition.getY());

        /* //  Kiểm tra rắn dap dau vao tuong
            || Xét rắn đi qua trái headPosition.getX() < 0
            || Dùng current Height để xét rắn đi xuống dưới đập vào tường currentHeightY >= gameboard.getHeight() - Độ cao nên xét trục y
            || Dùng current Width để xét rắn đi qua phải đập vào tường currentWidthX >= gameboard.getWidth() - Độ dài nên xét trục x
            || Xét rắn đi lên trên đập vào tường headPosition.getY < 0 
         */
        if (this.headPosition.getX() < 0 || currentHeightY >= gameboard.getHeight() || this.headPosition.getY() < 0 || currentWidthX >= gameboard.getWidth()) {
            return false;
        }

        // Kill Myselft
        for (int i = 1; i < this.snakeBody.size(); i++) {
            // Xét rắn tự va vào đuôi
            if (this.headPosition.getX() == this.snakeBody.get(i).getX() && this.headPosition.getY() == this.snakeBody.get(i).getY()) {
                return false;
            }
        }
        return true;
    }

    // Snake eat food 
    public boolean isSnakeEat(Food food) {
        // Xet ran da an tra ve true
        if (this.headPosition.getX() == food.getPosition().getX() && this.headPosition.getY() == food.getPosition().getY()) {
            return true;
        } else {
            return false;
        }
    }
}
