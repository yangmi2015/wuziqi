/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alphabeta;

import em.player;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import state.Chessaction;
import state.Chessstate;

/**
 *
 * @author yangmi
 */
public class algorism implements Runnable{
    state.Chessstate chestate;
    state.Chessaction chesaction;
    state.Chessaction nextaction;
    state.Chessstate maxvaluestate;
    state.Chessstate minvaluestae;
    int globallevel;

    public int getGloballevel() {
        return globallevel;
    }

    public void setGloballevel(int globallevel) {
        this.globallevel = globallevel;
    }

    public algorism(Chessstate chestate, Chessaction chesaction) {
        this.chestate = chestate;
        this.chesaction = chesaction;
        globallevel=2;
    }
    public state.Chessaction alphabetasearch(state.Chessstate a){
        
        int v;
//        state.Chessaction temp =new state.Chessaction(chestate);
        v=MIN_VALUE(a,Integer.MIN_VALUE,Integer.MAX_VALUE,em.player.Players.opp,globallevel);
//        v=MAX_VALUE(a,Integer.MAX_VALUE,Integer.MIN_VALUE,em.player.Players.opp,globallevel);
        return action(a,minvaluestae);
//         return action(a,maxvaluestate);
    }
    
    
    @Override
    public void run() {
        Chessaction temp=alphabetasearch(chestate);
        chesaction.set(temp.getX(), temp.getY(), player.Players.opp);
    }

    private int MIN_VALUE(Chessstate a, int alpha, int beta,em.player.Players b,int level) {
        if(level==0){
            //System.out.print(System.currentTimeMillis()+"\n");
//            for(char i[]:a.state){
//                for(char o:i){
//                    System.out.print(o);
//                }
//                System.out.println();
//            }
            int r=utility(a);
            //System.out.print(r);
            return r;
        }
        int v;
        v=Integer.MAX_VALUE;
        Vector lots=result(a,b);
        int temp;
        for(int i=0;i<lots.size();i++){
            b=em.player.Players.me;
//            v=min(v,MAX_VALUE((Chessstate) lots.get(i),alpha,beta,b,level-1));
            temp=MAX_VALUE((Chessstate) lots.get(i),alpha,beta,b,level-1);
            if(v>temp){
                v=temp;
                if(level==globallevel){
                    minvaluestae=(Chessstate) lots.get(i);
                    
                }
                }
            
            
            if(v<=alpha)return v;
            beta=min(beta,v);
        }
            return v;
    }



    private int MAX_VALUE(Chessstate a, int alpha, int beta,em.player.Players b,int level) {
         if(level==0)return utility(a);
        int v;
        v=Integer.MIN_VALUE;
        Vector lots=result(a,b);
        int temp;
        for(int i=0;i<lots.size();i++){
            b=em.player.Players.opp;
            //v=max(v,MIN_VALUE((Chessstate) lots.get(i),alpha,beta,b,--level));
            
            
            
            temp=MIN_VALUE((Chessstate) lots.get(i),alpha,beta,b,level-1);
            if(v<temp){
                v=temp;
                if(level==globallevel){
                    maxvaluestate=(Chessstate) lots.get(i);
                }
                }
            
            
            
            if(v>=beta)return v;
            alpha=max(beta,v);
        }
            return v;
    }

    private Vector result(Chessstate a,em.player.Players b) {
        Vector result =new Vector(100);
        char token;
        if(b==em.player.Players.me){
            token='*';
        }else{
            token='#';
        }
        for(int i=0;i<=20;i++){
            for(int j=0;j<=20;j++){
                if(a.state[i][j]!=0)continue;
                if(
                   a.state[i==0?0:i-1][j]!=0||
                   a.state[i==0?0:i-1][j==0?0:j-1]!=0||
                   a.state[i==0?0:i-1][j==20?20:j+1]!=0||
                   a.state[i==20?20:i+1][j]!=0||
                   a.state[i==20?20:i+1][j==20?20:j+1]!=0||
                   a.state[i==20?20:i+1][j==0?0:j-1]!=0||
                   a.state[i][j==20?20:j+1]!=0||
                   a.state[i][j==0?0:j-1]!=0
                 
                   )
                {
                    Chessstate temp=new Chessstate();
                    for(int q=0;q<=20;q++){
                        for(int p=0;p<=20;p++){
                            temp.state[q][p]=a.state[q][p];
                        }
                    }
                    temp.state[i][j]=token;
                    result.add(temp);
                }
                
            }
        }
        
        return result;
    }

   

    private int utility(Chessstate a) {
//       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for(char []g:a.state){
            for(char p:g){
                System.out.print(p);
            }
            System.out.println();
        }
       int v = 0;
       int vme=0;
       int vopp=0;
        Vector line = new Vector();
        for(int i=0;i<=20;i++){
            StringBuilder buffera=new StringBuilder();
            StringBuilder bufferb=new StringBuilder();
            for(int j=0;j<=20;j++){
                buffera.append(a.state[i][j]);
                bufferb.append(a.state[j][i]);
            }
            line.add(buffera);
            line.add(bufferb);
        }
        for(int i=0;i<=40;i++){
            StringBuilder buffera=new StringBuilder();
            StringBuilder bufferb=new StringBuilder();
            for(int j=0;j<=(i>20?20:i);j++){
                if(i<=20){
                buffera.append(a.state[i-j][j]);
                bufferb.append(a.state[20-j][i-j]);
                }else{
                    if(j<i-20)continue;
                    buffera.append(a.state[i-j][j]);
                    bufferb.append(a.state[20-j][i-j]);
                }
            }
            line.add(buffera);
            line.add(bufferb);
        }
//        for(int i=0;i<line.size();i++){
//            System.out.println(line.get(i));
//        }
        for(int i=0;i<line.size();i++){
//           System.out.print(i);
//           System.out.println(line.get(i));
            
            vme+=count(Pattern.compile("\\x00\\*\\x00").matcher((CharSequence) line.get(i)))*10;
            vme+=count(Pattern.compile("#\\*\\x00").matcher((CharSequence) line.get(i)))*1;
            vme+=count(Pattern.compile("\\x00\\*#").matcher((CharSequence) line.get(i)))*1;
            vme+=count(Pattern.compile("\\x00\\*\\*\\x00").matcher((CharSequence) line.get(i)))*100;
            vme+=count(Pattern.compile("#\\*\\*\\x00").matcher((CharSequence) line.get(i)))*10;
            vme+=count(Pattern.compile("\\x00\\*\\*#").matcher((CharSequence) line.get(i)))*10;
            vme+=count(Pattern.compile("\\x00\\*\\*\\*\\x00").matcher((CharSequence) line.get(i)))*1000;
            vme+=count(Pattern.compile("#\\*\\*\\*\\x00").matcher((CharSequence) line.get(i)))*100;
            vme+=count(Pattern.compile("\\x00\\*\\*\\*#").matcher((CharSequence) line.get(i)))*100;
            vme+=count(Pattern.compile("\\x00\\*\\*\\*\\*\\x00").matcher((CharSequence) line.get(i)))*10000;
            vme+=count(Pattern.compile("#\\*\\*\\*\\*\\x00").matcher((CharSequence) line.get(i)))*1000;
            vme+=count(Pattern.compile("\\x00\\*\\*\\*\\*#").matcher((CharSequence) line.get(i)))*1000;
            vme+=count(Pattern.compile("\\*\\*\\*\\*\\*").matcher((CharSequence) line.get(i)))*100000;
            
            vopp+=count(Pattern.compile("\\x00#\\x00").matcher((CharSequence) line.get(i)))*10;
            vopp+=count(Pattern.compile("\\*#\\x00").matcher((CharSequence) line.get(i)))*1;
            vopp+=count(Pattern.compile("\\x00#\\*").matcher((CharSequence) line.get(i)))*1;
            vopp+=count(Pattern.compile("\\x00##\\x00").matcher((CharSequence) line.get(i)))*100;
            vopp+=count(Pattern.compile("\\*##\\x00").matcher((CharSequence) line.get(i)))*10;
            vopp+=count(Pattern.compile("\\x00##\\*").matcher((CharSequence) line.get(i)))*10;
            vopp+=count(Pattern.compile("\\x00###\\x00").matcher((CharSequence) line.get(i)))*1000;
            vopp+=count(Pattern.compile("\\*###\\x00").matcher((CharSequence) line.get(i)))*100;
            vopp+=count(Pattern.compile("\\x00###\\*").matcher((CharSequence) line.get(i)))*100;
            vopp+=count(Pattern.compile("\\x00####\\x00").matcher((CharSequence) line.get(i)))*10000;
            vopp+=count(Pattern.compile("\\*####\\x00").matcher((CharSequence) line.get(i)))*1000;
            vopp+=count(Pattern.compile("\\x00####\\*").matcher((CharSequence) line.get(i)))*1000;
            vopp+=count(Pattern.compile("#####").matcher((CharSequence) line.get(i)))*100000;
        }
        v=2*vme-3*vopp;
        System.out.println();
        System.out.print("--"+v);
       return v;
    }

    private Chessaction action(Chessstate a, Chessstate valuestate) {
        Chessaction result =new Chessaction(null);
        
        for(int i=0;i<=20;i++){
            for(int j=0;j<=20;j++){
                if(a.state[i][j]!=valuestate.state[i][j]){
                result.setPlayer(player.Players.opp);
                result.setX(i);
                result.setY(j);
            }
        }
    }
    return result;
}

    private int count(Matcher matcher) {
        int v=0;
        while(matcher.find()){
            v++;
        }
        return v;
    }
}
