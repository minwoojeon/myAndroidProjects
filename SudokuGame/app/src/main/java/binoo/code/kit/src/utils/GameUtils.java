package binoo.code.kit.src.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by binoo on 2018-01-07.
 */

public final class GameUtils {

    // 9x9 스도쿠 만들기
    public Integer[][] makeSudoku9(){
        ArrayList<Integer> sudokuList = new ArrayList<Integer>();
        for(int i=1; i<10; i++){
            sudokuList.add(i);
        }
        Integer[][] X1 = {{0,1,0}, {0,0,1}, {1,0,0}};
        Integer[][] X2 = {{0,0,1}, {1,0,0}, {0,1,0}};
        Integer[][] S0 = new Integer[9][9];
        Integer[][] SX = new Integer[6][6];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int index = (int)(Math.random()*sudokuList.size());
                S0[i][j] = sudokuList.get(index);
                sudokuList.remove(index);
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                S0[i][j+3] = (X1[i][0]*S0[0][j])+(X1[i][1]*S0[1][j])+(X1[i][2]*S0[2][j]);
                S0[i][j+6] = (X2[i][0]*S0[0][j])+(X2[i][1]*S0[1][j])+(X2[i][2]*S0[2][j]);
                S0[i+3][j] = (S0[i][0]*X1[0][j])+(S0[i][1]*X1[1][j])+(S0[i][2]*X1[2][j]);
                S0[i+6][j] = (S0[i][0]*X2[0][j])+(S0[i][1]*X2[1][j])+(S0[i][2]*X2[2][j]);

                SX[i][j] = (X1[i][0]*S0[0][j])+(X1[i][1]*S0[1][j])+(X1[i][2]*S0[2][j]);
                SX[i][j+3] = (X2[i][0]*S0[0][j])+(X2[i][1]*S0[1][j])+(X2[i][2]*S0[2][j]);
                SX[i+3][j] = (X1[i][0]*S0[0][j])+(X1[i][1]*S0[1][j])+(X1[i][2]*S0[2][j]);
                SX[i+3][j+3] = (X2[i][0]*S0[0][j])+(X2[i][1]*S0[1][j])+(X2[i][2]*S0[2][j]);
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                S0[i+3][j+3] = (SX[i][0]*X1[0][j])+(SX[i][1]*X1[1][j])+(SX[i][2]*X1[2][j]);
                S0[i+3][j+6] = (SX[i][0+3]*X1[0][j])+(SX[i][1+3]*X1[1][j])+(SX[i][2+3]*X1[2][j]);
                S0[i+6][j+3] = (SX[i+3][0]*X2[0][j])+(SX[i+3][1]*X2[1][j])+(SX[i+3][2]*X2[2][j]);
                S0[i+6][j+6] = (SX[i+3][0+3]*X2[0][j])+(SX[i+3][1+3]*X2[1][j])+(SX[i+3][2+3]*X2[2][j]);
            }
        }
        return S0;
    }

    // 4x4 스도쿠 만들기
    public Integer[][] makeSudoku4(){
        // 스도쿠를 만든다.
        // 아래로직을 횟수만큼 반복한다.
		/*
		1. 총 4개 블럭이다. (4x4)- 행렬연산
		Si = 랜덤으로 만들기 {2x2}
		2. Mask 필드를 만든다.
		 * */
        ArrayList<Integer> sudokuList = new ArrayList<Integer>();
        for(int i=1; i<5; i++){
            sudokuList.add(i);
        }
        Integer[][] X1 = {{0,1}, {1,0}};
        Integer[][] S0 = new Integer[4][4];
        Integer[][] SX = new Integer[2][2];
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                int index = (int)(Math.random()*sudokuList.size());
                S0[i][j] = sudokuList.get(index);
                sudokuList.remove(index);
            }
        }
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                S0[i][j+2] = (X1[i][0]*S0[0][j])+(X1[i][1]*S0[1][j]);
                S0[i+2][j] = (S0[i][0]*X1[0][j])+(S0[i][1]*X1[1][j]);

                SX[i][j] = (X1[i][0]*S0[0][j])+(X1[i][1]*S0[1][j]);
            }
        }
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                S0[i+2][j+2] = (SX[i][0]*X1[0][j])+(SX[i][1]*X1[1][j]);
            }
        }
        return S0;
    }
    // 게임 불러오기
    private Integer[][] loadSudoku(int code){
        return null;
    }
    // 게임 저장하기
    private boolean saveSudoku( HashMap<String, Object> map ){
        return true;
    }
    // 게임 삭제하기
    private boolean deleteSudoku( HashMap<String, Object> map ){
        return true;
    }

    private Integer[][] makeMaskMap( int len, int level ){
        Integer[][] mask = new Integer[len][len];
        ArrayList<Integer> maskList = new ArrayList<Integer>();
        for(int i=0; i<len; i++){
            maskList.add(i);
        }
        for (int i=0; i<len; i++){
            ArrayList<Integer> bufList = new ArrayList<Integer>();
            bufList.addAll( maskList );
            for (int j=0; j<len; j++){
                mask[i][j] = 0;
            }
            for (int j=level; j>0; j--){
                int index = (int)(Math.random()*bufList.size());
                mask[i][bufList.get(index)] = 1;         // 안보이도록 설정
                bufList.remove(index);
            }
        }
        return mask;
    }
    // 일반 스도쿠 맵 생성하기(마스킹)
    public HashMap<Integer, Object> makeNormalGameMap( int type ){
        /*
        게임 맵
        =========================================================
        인자              설명              값
        s              성공여부            0 또는 1
        y              타입                0x10 : 4스도쿠, 0x20 : 노말9스도쿠, 0x30 문자 스도쿠, 0x40 X스도쿠, 0x50 부등호스도쿠, 0x60 킬러스도쿠, 0x70 천사와 악마의 부등호 스도쿠
                                              * 부가적으로 난이도에 따라 +1씩 함(입문/간단한 난이도/생각이 필요한 난이도/ 고민이 필요한 난이도/ 어려운 난이도/ 극한의 난이도)
        m              스도쿠맵            4x4 / 9x9 스도쿠맵
        g              입력마스크          4x4 / 9x9 스도쿠맵
        d              최초생성시간        long
        n              저장PK              int
        e              메모내역            Map<Integer, List<Integer>> -> 73=>{1,3,8} 7행3열 메모내용은 1,3,8
        u              플레이시간          long
        =========================================================

        난이도에 따른 마스킹 제작
        =========================================================
        9x9 노말/ 문자
        난이도                     보이는 숫자 갯수/총갯수       행마다수치
        입문                          72/81                       8
        간단한 난이도                 54/81                       6
        무난한 난이도                 45/81                       5
        생각이 필요한 난이도          36/81                       4
        고민이 필요한 난이도          27/81                       3
        손끝이 시려운 난이도          18/81                       2
        절대영도                       9/81                       1

        9x9 킬러/부등호 스도쿠
        난이도                     보이는 숫자 갯수/총갯수       행마다수치
        입문                          72/81                       8
        간단한 난이도                 54/81                       6
        무난한 난이도                 36/81                       4
        생각이 필요한 난이도          27/81                       3
        고민이 필요한 난이도          18/81                       2
        손끝이 시려운 난이도          9/81                        1
        절대영도                       0/81                       0

        9x9 천사와 악마의 부등호 스도쿠
        난이도                     보이는 숫자 갯수/총갯수       행마다수치   거짓(악마)부등호 갯수
        입문                          72/81                       8               1
        간단한 난이도                 54/81                       6               2
        무난한 난이도                 36/81                       4               4
        생각이 필요한 난이도          27/81                       3               6
        고민이 필요한 난이도          18/81                       2               9
        손끝이 시려운 난이도          9/81                        1               15
        절대영도                       0/81                       0               27

        * */
        HashMap<Integer, Object> gameMap = new HashMap<Integer, Object>();
        gameMap.put( 's'-0, "F" );
        // 타입
        gameMap.put( 'y'-0, type );
        // 스도쿠 맵
        int rotation = (int)(Math.random()*3);
        Integer[][] sudoku = null;
        if (type <= 0x19){
            // 4x4 스도쿠
            sudoku = this.makeSudoku4();
        } else {
            sudoku = this.makeSudoku9();
        }
        if (sudoku == null){
            // 에러
            return gameMap;
        }
        if (rotation == 0){
            // 90도 회전
            Integer bufSudoku[][] = new Integer[sudoku.length][sudoku[0].length];
            for (int i=0; i<sudoku.length; i++){
                for (int j=0; j<sudoku[0].length; j++){
                    bufSudoku[i][j] = sudoku[j][i];
                }
            }
            sudoku = bufSudoku;
            bufSudoku = null;
        } else if (rotation == 1){
            // 180도 회전
            Integer bufSudoku[][] = new Integer[sudoku.length][sudoku[0].length];
            for (int i=0; i<sudoku.length; i++){
                for (int j=0; j<sudoku[0].length; j++){
                    bufSudoku[i][j] = sudoku[sudoku[0].length-1-j][sudoku[0].length-1-i];
                }
            }
            sudoku = bufSudoku;
            bufSudoku = null;
        }
        gameMap.put( 'm'-0, sudoku );
        // 마스크
        int level = type % 0x10;
        int play = type / 0x10;
        // 4스도쿠, 0x20 : 노말9스도쿠, 0x30 문자 스도쿠, 0x40 X스도쿠, 0x50 부등호스도쿠, 0x60 킬러스도쿠, 0x70 천사와 악마의 부등호 스도쿠
        /*
        난이도                     보이는 숫자 갯수/총갯수       행마다수치
        입문                          12/16                      3
        간단한 난이도                 8/16                       2
        무난한 난이도                 4/16                       1

        난이도                     보이는 숫자 갯수/총갯수       행마다수치
        입문                          72/81                       8
        간단한 난이도                 54/81                       6
        무난한 난이도                 45/81                       5
        생각이 필요한 난이도          36/81                       4
        고민이 필요한 난이도          27/81                       3          2
        손끝이 시려운 난이도          18/81                       2          1
        절대영도                       9/81                       1          0      킬러/부등호/천사악마
        * */
        Integer[][] mask = null;
        if (play == 1){
            // 4x4 라면
            int len = 4;
            switch (level){
                case 0:
                    mask = makeMaskMap(len, 1);
                    break;
                case 1:
                    mask = makeMaskMap(len, 2);
                    break;
                case 2:
                    mask = makeMaskMap(len, 3);
                    break;
                default:
                    break;
            }
        } else {
            // 9x9 라면
            int len = 9;
            switch (level) {
                case 0:
                    mask = makeMaskMap(len, 1);
                    break;
                case 1:
                    mask = makeMaskMap(len, 2);
                    break;
                case 2:
                    mask = makeMaskMap(len, 3);
                    break;
                case 3:
                    mask = makeMaskMap(len, 4);
                    break;
                case 4:
                    mask = makeMaskMap(len, 5);
                    break;
                case 5:
                    mask = makeMaskMap(len, 6);
                    break;
                case 6:
                    mask = makeMaskMap(len, 7);
                    break;
                case 7:
                    mask = makeMaskMap(len, 8);
                    break;
                case 8:
                    mask = makeMaskMap(len, 9);
                    break;
                default:
                    break;
            }
        }
        gameMap.put( 'g'-0, mask );
        // 최초생성
        gameMap.put( 'd'-0, new SimpleDateFormat("yyyy mm dd hh MM ss").format(new Date()) );

        gameMap.put( 's'-0, "S" );
        return gameMap;
    }
}
