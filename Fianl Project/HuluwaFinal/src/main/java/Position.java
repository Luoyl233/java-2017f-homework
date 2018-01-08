

public class Position {

    private int x;
    private int y;

    public Creature getHolder() {
        return holder;
    }

    public void setHolder(Creature holder) {
        this.holder = holder;
        holder.setPosition(this);
    }

    public void removeHolder() {
        if(this.holder != null) {
            this.holder = null;
        }

    }

    private Creature holder;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y){
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        if(holder != null)
            return holder.toString();
        else
            return "口";
    }

    boolean legal() {
        return (x >= 0) && (y >= 0);
    }
}