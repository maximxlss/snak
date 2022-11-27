package com.xls.snak;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.xls.utils.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SnakLogic {
    final boolean wrapping;
    final Random randgen;

    private final short[][] board;
    private final short[][] board_orient;
    private int tail_x;
    private int tail_y;
    private Queue<Short> snake;
    private int head_x;
    private int head_y;

    private boolean game_over;

    public SnakLogic(boolean wrapping, int width, int height) {
        this(wrapping, width, height, null);
    }

    public SnakLogic(boolean wrapping, int width, int height, Long seed) {
        this.wrapping = wrapping;
        if (seed != null) {
            this.randgen = new Random(seed);
        } else {
            this.randgen = new Random();
        }

        this.board = new short[width][height];
        this.board_orient = new short[width][height];
        InitSnake(width, height);
        SpawnFruit();
    }

    private void InitSnake(int width, int height) {
        int middle_x = width / 2;
        int middle_y = height / 2;
        this.tail_x = middle_x;
        this.tail_y = middle_y - 1;
        board[tail_x][tail_y] = TileType.Tail;
        board_orient[tail_x][tail_y] = Direction.Up;
        this.snake = new Queue<>();
        snake.addLast(Direction.Up);
        this.head_x = middle_x;
        this.head_y = middle_y;
        board[head_x][head_y] = TileType.Head;
        board_orient[head_x][head_y] = Direction.Up;
    }

    public void SpawnFruit() {
        int x, y;
        do {
            x = randgen.nextInt(0, board.length);
            y = randgen.nextInt(0, board[0].length);
        } while (board[x][y] != TileType.Empty);

        board[x][y] = TileType.Fruit;
        board_orient[x][y] = Direction.Up;
    }

    public Event Move(short dir) {
        short head_dir = snake.last();
        if (dir == Direction.Inverse(head_dir) || dir == -1)
            dir = head_dir;
        snake.addLast(dir);

        // remove old head from the board
        if (dir == head_dir) {
            board[head_x][head_y] = TileType.Body;
            board_orient[head_x][head_y] = dir;
        } else {
            board[head_x][head_y] = TileType.BodyTurn;
            board_orient[head_x][head_y] = head_dir;
            if (dir != Direction.TurnRight(head_dir)) {
                board_orient[head_x][head_y] = Direction.TurnRight(board_orient[head_x][head_y]);
            }
        }

        // move head coordinates
        switch (dir) {
            case (Direction.Up):
                head_y++;
                break;
            case (Direction.Right):
                head_x++;
                break;
            case (Direction.Down):
                head_y--;
                break;
            case (Direction.Left):
                head_x--;
                break;
        }

        if (wrapping) {
            head_x %= board.length;
            if (head_x < 0)
                head_x += board.length;
            head_y %= board[0].length;
            if (head_y < 0)
                head_y += board[0].length;
        }
        if (head_x >= board.length || head_x < 0 || head_y >= board[0].length || head_y < 0 ||
                board[head_x][head_y] == TileType.Wall || board[head_x][head_y] == TileType.Body || board[head_x][head_y] == TileType.BodyTurn) {
            game_over = true;
            return Event.GameOver;
        }
        boolean ate_fruit = board[head_x][head_y] == TileType.Fruit;
        board[head_x][head_y] = TileType.Head;
        board_orient[head_x][head_y] = dir;

        if (!ate_fruit) {
            // delete tail
            if (board[tail_x][tail_y] == TileType.Tail)
                board[tail_x][tail_y] = TileType.Empty;
            short tail_dir = snake.removeFirst();
            // move tail coordinates
            switch (tail_dir) {
                case (Direction.Up):
                    tail_y++;
                    break;
                case (Direction.Right):
                    tail_x++;
                    break;
                case (Direction.Down):
                    tail_y--;
                    break;
                case (Direction.Left):
                    tail_x--;
                    break;
            }
            tail_x %= board.length;
            if (tail_x < 0)
                tail_x += board.length;
            tail_y %= board[0].length;
            if (tail_y < 0)
                tail_y += board[0].length;
            // add tail
            board[tail_x][tail_y] = TileType.Tail;
            board_orient[tail_x][tail_y] = snake.first();

            return Event.None;
        } else
            return Event.AteFruit;
    }

    public short[][] GetBoard() {
        return board;
    }

    public boolean IsGameOver() {
        return game_over;
    }

    public void render(@NotNull SpriteBatch batch, float x, float y, float width, float height, @NotNull Array<TextureRegion> textures) {
        float tile_width = width / board.length;
        float tile_height = height / board[0].length;
        for (int tx = 0; tx < board.length; tx++)
            for (int ty = 0; ty < board[0].length; ty++) {
                float tile_x = x + tile_width * tx;
                float tile_y = y + tile_height * ty;
                short tile_type = board[tx][ty];
                float tile_orient = board_orient[tx][ty] * -90;
                batch.draw(textures.get(tile_type),
                        tile_x, tile_y,
                        tile_width / 2, tile_height / 2,
                        tile_width, tile_height,
                        1, 1,
                        tile_orient);
            }
    }

    public enum Event {
        None, AteFruit, GameOver
    }
}
