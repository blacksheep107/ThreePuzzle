package Puzzle;

import Analysis.JSONAnalysis;

import java.util.*;

public class PlayPuzzle {
    private Map<Integer,Integer> route=new HashMap<Integer, Integer>();//key子结点，value父结点
    private String arr=new String();
    private String brr= "123456789";
    private int des=123456789;//目标状态
    /**
     * 空格出现在0-8的位置中可以和哪些位置交换，顺序上左下右wasd
     */
    private static final int[][] changeId=new int[][]{
            {-1, -1, 3, 1}, {-1, 0, 4, 2}, {-1, 1, 5, -1},
            {0, -1, 6, 4}, {1, 3, 7, 5}, {2, 4, 8, -1},
            {3, -1, -1, 7}, {4, 6, -1, 8}, {5, 7, -1, -1}
    };
    /**
     * 牌号到目标位置的曼哈顿距离
     */
    private static final int[][] STEPTODES=new int[][]{
            {0,0,0,0,0,0,0,0,0,0},{0,0,1,2,1,2,3,2,3,4},
            {0,1,0,1,2,1,2,3,2,3},{0,2,1,0,3,2,1,4,3,2},
            {0,1,2,3,0,1,2,1,2,3},{0,2,1,2,1,0,1,2,1,2},
            {0,3,2,1,2,1,0,3,2,1},{0,2,3,4,1,2,3,0,1,2},
            {0,3,2,3,2,1,2,1,0,1},{0,4,3,2,3,2,1,2,1,0}

    };
    //状态是否出现过
    private Map<Integer, Boolean>mymap=new HashMap<Integer,Boolean>();
    //优先队列，存储状态，每次取估价值最大的状态
    private PriorityQueue<Node> que=new PriorityQueue<Node>();
    //移动位置，到达该状态做的移动
    public  Map<Integer,Character>operation_to_the_state=new HashMap<Integer, Character>();
    //答案：操作序列
    public  Vector<Character> op=new Vector<Character>();
    //答案
    public static String getop=new String();
    //操作方向
    private static char[] dis={'w','a','s','d'};
    //自由交换位置
    public static int[] best;
    //特判交换之后直接达到目标，需要加一个操作
    public int special_add;

    public class Node implements Comparable<Node>{
        /**
         * num为状态序列，
         * step为已经移动的步数，
         * cost为未在正确位置上的块数加步数，即估价函数
         * zeroPos为空格位置
         * operation为到达该节点做的操作
         */
        int num,step,cost,zeroPos;
        char opration;
        Node(int n,int s,int p,char op){
            num=n;step=s;zeroPos=p;opration=op;
            setCost();
        }
        void setCost(){
            cost=step;
            /*int t=num,tdes=des,i=8;
            while(t!=0){
                if(t%10==tdes%10)   cost++;
                t/=10;
                tdes/=10;
            }*/
            int t=num,i=9;
            while(t!=0){
                cost+=STEPTODES[t%10][i--];
                t/=10;
            }
        }

        @Override
        public int compareTo(Node o) {
            return cost-o.cost;
        }
    }

    /**
     * 交换位置
     * @param ch 状态
     * @param a 交换位置
     * @param b 交换位置
     * @return 返回状态，注意toCharArray会创建一个新数组，不是在原String上交换
     */
    private  String swap(char[]ch, int a, int b){
        char c=ch[a];
        ch[a]=ch[b];
        ch[b]=c;
        return String.valueOf(ch);
    }

    /**
     * bfs 启发式查找
     * @param start 开始状态
     * @param zeroPos 空格位置
     * @return 步数
     */
    public  int bfsHash(int start, int zeroPos){
        special_add=0;
        char op='f';//开始节点没有操作状态
        int pastnum=start;
        Node tempN=new Node(start,0,zeroPos,op);//为开始状态创建节点
        que.add(tempN);//压入优先级队列
        route.put(start,-1);
        mymap.put(start,true);//标记开始状态被访问过
        while(que.peek()!=null) {//非空，若空则无解
            tempN = que.peek();//从队列里取一个节点
            que.poll();
            /**
             * 如果步数达到强制交换，在第step步执行前发生
             */
            if(JSONAnalysis.step==tempN.step){//步数从0开始算
                JSONAnalysis.step=-1;//保证只进行一次强制交换
                findRoute(tempN.num);//记录前半段路径

                arr=Integer.toString(tempN.num);
                if(arr.length()<9)  arr="0"+arr;//空格在最前时
                //System.out.println("Before ForceSwap:"+arr);
                //TODO
                //arr=swap(arr.toCharArray(),4,8);
                arr=swap(arr.toCharArray(),JSONAnalysis.swap.get(0)-1,JSONAnalysis.swap.get(1)-1);
                //System.out.println("After ForceSwap:"+arr);
                if(!haveAnswer()){//强制交换无解
                    best=makeBestExchange();//找自由交换的最好方案
                    arr=swap(arr.toCharArray(),best[0], best[1]);
                }
                if(arr.equals(brr)){//交换后为目标状态
                    route.put(des,-1);
                    operation_to_the_state.put(des,null);
                    special_add=1;//
                    return tempN.step+1;
                }
                que.clear();//清空队列，从当前状态重新开始搜索
                mymap.clear();//交换后的状态向所有方向交换的状态可能在之前出现过
                operation_to_the_state.clear();
                route.clear();//若不清空会导致无解
                int t=Integer.parseInt(arr);
                int zero=0;
                for(int i=0;i<arr.length();i++)
                    if(arr.charAt(i)=='0') {
                        zero=i;
                        break;
                    }
                Node tempM=new Node(t,tempN.step+1,zero,'f');
                route.put(t,-1);
                mymap.put(t,true);
                que.add(tempM);//放入新的开始节点
                continue;
            }

            //temp = String.format("%09d", tempN.num);//方便交换处理
            String temp="";
            int pos = tempN.zeroPos, num;
            if(pos==0){
                temp+="0";
            }
            Integer s=tempN.num;
            temp+=s.toString();
            pastnum= tempN.num;//正要进行交换
            for (int i = 0; i < 4; i++) {
                if (changeId[pos][i] != -1) {//可以向该方向移动
                    temp=swap(temp.toCharArray(), pos, changeId[pos][i]);//移动
                    num = Integer.parseInt(temp);//移动后的状态
                    if (num==des){//到达目标状态
                        route.put(des,pastnum);
                        operation_to_the_state.put(des,dis[i]);
                        return tempN.step + 1;//步数
                    }
                    if (mymap.get(num) == null) {//不是目标状态，且第一次出现
                        Node tempM = new Node(num, tempN.step + 1, changeId[pos][i],dis[i]);
                        operation_to_the_state.put(num,dis[i]);//记录操作
                        route.put(num,pastnum);//记录当前状态父节点
                        que.add(tempM);//压入队列
                        mymap.put(num, true);//标记该状态被访问过
                    }
                    temp=swap(temp.toCharArray(), pos, changeId[pos][i]);//回滚状态
                }
            }
        }
        return -1;//无解
    }

    /**
     * 获得解法
     */
    public void playPuzzle(int[] mat){//传入问题序列
        op.clear();
        operation_to_the_state.clear();
        arr="";
        getop="";
        int n=0,k,b;
        boolean[] d=new boolean[10];

        for(int i=1;i<=9;i++){
            d[mat[i]]=true;//出现过的数字
            arr+=(mat[i]);
        }
        System.out.println("问题序列："+arr);
        for(k=0;k<9;k++)
            if(arr.charAt(k)=='0')break;//找0
        /**
         * 重置目标，找没出现的数字，把目标中的这个数置为0
         */
        for(int i=1;i<=9;i++){
            if(d[i]==false){
                StringBuffer sb=new StringBuffer(brr);
                sb.replace(i-1,i,"0");
                brr=sb.toString();
                break;
            }
        }
        System.out.println("目标序列："+brr);//目标

        des=Integer.parseInt(brr);
        n=Integer.parseInt(arr);//原
        b=bfsHash(n,k);
        System.out.println("步数："+b);//步数，-1则找不到

        findRoute(des);//找路径
        for(int i=0;i< op.size();i++)    getop+=op.get(i);
        if(special_add==1)  getop+='w';
        //System.out.println(getop);
    }
    /**
     * 找路径
     */
    public void findRoute(int nowdes){
        int pastsize=op.size();
        if(operation_to_the_state.get(nowdes)==null)    return;
        op.add(operation_to_the_state.get(nowdes));
        if(route.get(nowdes)==null) return ;
        int find_route= route.get(nowdes);
        while(route.get(find_route)!=null&&route.get(find_route)!=-1){
            op.add(operation_to_the_state.get(find_route));
            find_route=route.get(find_route);
        }
        //System.out.print("noReverse:");
        //for(int i=pastsize;i<op.size();i++) System.out.print(op.get(i));
        //System.out.println();
        for (int i = pastsize; i < (op.size()-pastsize) / 2+pastsize; i++) {//倒转
            Character temp = op.get(i);
            op.setElementAt(op.get(op.size()-i-1+pastsize),i);
            op.setElementAt(temp,op.size()-i-1+pastsize);
        }
        //System.out.print("Reverse:");
        for(int i=pastsize;i<op.size();i++) System.out.print(op.get(i));
        System.out.println();
    }
    /**
     * 是否有解,arr,brr
     */
    public boolean haveAnswer(){
        int[] d1=new int[8];//逆序对数组
        int index1=0;
        for(int i=0;i<9;i++){
            if(arr.charAt(i)!='0'){
                d1[index1++]=arr.charAt(i)-'0';
            }
        }
        if(getInversion(d1)%2!=0)  return false;//逆序对奇偶不同则无解，目标逆序对数一定为0
        return true;
    }

    /**
     * @return 逆序对数
     */
    public int getInversion(int a[]){
        int cnt=0;
        for(int i=0;i<7;i++){
            for(int j=i+1;j<8;j++){
                if(a[i]>a[j])   cnt++;
            }
        }
        return cnt;
    }
    /**
     * 只能交换中间隔0,2,4,6，找出最好交换策略
     * 0有7种，2有5种，4有3种，6有1种
     * 策略：计算每种交换后状态的估价函数
     */
    public int[] makeBestExchange(){
        int[] theBestMethod=new int[2];
        int max=-1,zero=0;
        String arrn="";
        for(int i=0;i<9;i++){
            if(arr.charAt(i)!='0')  arrn+=arr.charAt(i);
            else    zero=i;
        }
        //arrn共8个数，无0
        for(int i=0;i<7;i++){
            for(int j=i+1;j<8;j++){
                if(j-i==1||j-i==3||j-i==5||j-i==7){//可以交换
                    int c=0;
                    String temp=swap(arrn.toCharArray(),i,j);
                    String insertzero=temp.substring(0,zero);//插入0
                    insertzero+="0";
                    insertzero+=temp.substring(zero);
                    for(int k=0;k<9;k++){//计算在位的块数
                        c+=STEPTODES[insertzero.charAt(k)-'0'][k];
                    }
                    //System.out.println("insertZero="+insertzero+" "+"brr="+brr+" "+"c="+c);
                    if(c>max) {//更合适的交换位置
                        max=c;
                        theBestMethod[0]=i;
                        theBestMethod[1]=j;
                        if(i>=zero){//i在前，若0在更前面，两个位置都往后推一位
                            theBestMethod[0]++;
                            theBestMethod[1]++;
                        }else if(j>=zero){//0只在j前面
                            theBestMethod[1]++;
                        }
                    }
                }
            }
        }
        return theBestMethod;
    }
}
