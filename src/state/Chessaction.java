/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import em.player;



/**
 *
 * @author yangmi
 */
public class Chessaction {
    private int X;
    private int Y;
    private em.player.Players player;
    private Chessstate state;

    public Chessaction(Chessstate state) {
        this.state = state;
        player =em.player.Players.me;
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public void setPlayer(player.Players player) {
        this.player = player;
    }

    
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public player.Players getPlayer() {
        return player;
    }

    public Chessstate getState() {
        return state;
    }
    
    public void set(int x,int y,em.player.Players a){
        int tempX=X;
        int tempY=Y;
        em.player.Players temp=player;
        X=x;
        Y=y;
        player=a;
        if(!state.actioninstate(this))state.change(this);
        else{
            X=tempX;
            Y=tempY;
            player=temp;
        }
    }
   
    
}
