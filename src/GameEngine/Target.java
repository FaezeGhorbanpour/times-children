
package GameEngine;
public abstract class Target {
    protected boolean isAlive = true;
    protected int life;
    protected int price;
    protected int baseLife;
    protected int type;
    protected Integer firstTimeOfShout;
    protected int timeBetweenShouting;
    protected int powerForInfantry ;
    protected int powerForTank;
    protected Path path;
    protected Cell cell;
    protected int fieldOfView;
    protected int teamID;
    protected Lane lane;
    protected String name;


    public int getLife() {
        return life;
    }

    public Path getPath() {
        return path;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPowerForTank() {
        return powerForTank;
    }

    public int getPowerForInfantry() {
        return powerForInfantry;
    }

    public void setFirstTimeOfShout(int time) {
        firstTimeOfShout = time;
    }

    public Cell getCell() {
        return cell;
    }

    public int getTeam() {
        return teamID;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getFieldOfView() {
        return fieldOfView;
    }



    public void attack(Target target, int time) {
        if (firstTimeOfShout == null || time - firstTimeOfShout >= timeBetweenShouting) {
            firstTimeOfShout = time;
            if (target.type == JudgeAbstract.ATTACKER_TANK)
                target.hurt(powerForTank);
            else
                target.hurt(powerForInfantry);
        }
    }


    public void attackHero(Hero hero, int time)  {
        if (firstTimeOfShout == null || time - firstTimeOfShout >= timeBetweenShouting) {
            firstTimeOfShout = time;
            hero.hurt(powerForInfantry, time);
        }
    }


    public boolean hurt(int lifedec){
        life -= lifedec;
        if(life > 0)
            return true;
        life = 0;
        isAlive = false;
        return false;
    }

    public boolean isInFieldOfView(int row,int col){
       return (Math.abs(row-cell.getRow())<=fieldOfView&&Math.abs(col-cell.getColumn())<=fieldOfView);
    }
}
