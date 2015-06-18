/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.util.Vector;
import java.util.regex.Pattern;

/**
 *
 * @author yangmi
 */
public class Chessstate {
    //* reprensent me
    //# represent opp
    //0 reprensent null;
    public char[][] state=new char[21][21];
    public boolean actioninstate(Chessaction a){
        if(state[a.getX()][a.getY()]==0)return false;
        else return true;
    }
    public void change(Chessaction a){
        if(a.getPlayer()==em.player.Players.me){
            state[a.getX()][a.getY()]='*';
        }else{
            state[a.getX()][a.getY()]='#';
        }
    }
    public em.player.Players win(){
         Vector line = new Vector();
        for(int i=0;i<=20;i++){
            StringBuilder buffera=new StringBuilder();
            StringBuilder bufferb=new StringBuilder();
            for(int j=0;j<=20;j++){
                buffera.append(this.state[i][j]);
                bufferb.append(this.state[j][i]);
            }
            line.add(buffera);
            line.add(bufferb);
        }
        for(int i=0;i<=40;i++){
            StringBuilder buffera=new StringBuilder();
            StringBuilder bufferb=new StringBuilder();
            for(int j=0;j<=(i>20?20:i);j++){
                if(i<=20){
                buffera.append(this.state[i-j][j]);
                bufferb.append(this.state[20-j][i-j]);
                }else{
                    if(j<i-20)continue;
                    buffera.append(this.state[i-j][j]);
                    bufferb.append(this.state[20-j][i-j]);
                }
            }
            line.add(buffera);
            line.add(bufferb);
        }
        for(int i=0;i<line.size();i++){
//           System.out.print(i);
//           System.out.println(line.get(i));
            
            boolean a =Pattern.compile("\\*\\*\\*\\*\\*").matcher((CharSequence) line.get(i)).find();
            boolean b =Pattern.compile("#####").matcher((CharSequence) line.get(i)).find();
            if(a==true)return em.player.Players.me;
            if(b==true)return em.player.Players.opp;
    }
        return null;
        
    }
}
