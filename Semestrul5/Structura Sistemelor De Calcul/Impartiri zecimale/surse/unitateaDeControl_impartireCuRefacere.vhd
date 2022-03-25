----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 11/14/2021 06:29:41 PM
-- Design Name: 
-- Module Name: unitateaDeControl_impartireCuRefacere - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity unitateaDeControl_impartireCuRefacere is
generic(n: natural);
  Port ( 
  start,clk,rst: in std_logic;
  an: in std_logic;
  selmuxin: out std_logic;
  loadA,loadB,loadQ: out std_logic;
  shla,shlq: out std_logic;
  subb:out std_logic;
  term: out std_logic;
  nn: out std_logic_vector(3 downto 0)   
  );
end unitateaDeControl_impartireCuRefacere;

architecture Behavioral of unitateaDeControl_impartireCuRefacere is

type tip_stare is (idle,initializare,shiftareA,scadere,decizie,adunare,atribuirecat,comparare,stop);
signal stare: tip_stare;
signal cn: natural:=4;
signal t: std_logic:='0';
signal n1 : std_logic_vector(3 downto 0):="0000";
begin


process(clk,rst,start,an)
begin 
    if rising_edge(clk) then
        if rst = '1' then 
            stare<=idle;
         end if;
         
         case stare is 
            when idle =>
                                    t <= '0';
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';                                    
                                    subb <=  '0';
                                    selmuxin <= '0';
                                    n1<=(others =>'0');
                                    nn<="0000";
                                    cn <= 4;
                                    
                                    if start = '1'  then  
                                        stare <= initializare;
                                    else                                        
                                        stare <= idle;
                                    end if;
            when initializare =>
                                    loada <= '1';
                                    loadb <= '1';
                                    loadq <= '1';
                                    selmuxin <= '1';
                                    shla <= '0';
                                    shlq <= '0';                                   
                                    subb <=  '0';
                                    n1<="0000";
                                    stare <= shiftareA;                                   
            when shiftareA =>
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '1';
                                    shlq <= '0';                                    
                                    subb <=  '0';
                                    selmuxin <= '0';
                                    n1<="0000";
                                    stare <= scadere;  
                                    
            when scadere =>
                                    loada <= '1';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';
                                    subb <=  '1';
                                    selmuxin <= '0';
                                    
                                    n1<= n1 + 1;
                                    
                                    stare <= decizie;
            when decizie =>
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';
                                    subb <=  '0';
                                    selmuxin <= '0';
                                    
                                        if(an ='1') then 
                                            stare <= adunare;
                                        else 
                                            stare <= scadere;
                                        end if;    
            when adunare =>
                                    loada <= '1';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';
                                    subb <=  '0';
                                    n1<= n1 - 1;
                                    
                                    stare <= atribuirecat;
            when atribuirecat =>
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '1';
                                    subb <=  '0';
                                    nn<=n1;
                                    selmuxin <= '0';
                                    
                                    cn <= cn - 1;
                                    
                                    stare <= comparare;
            when comparare =>
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';
                                    subb <=  '0';
                                    selmuxin <= '0';
                                    
                                    if cn = 0 then 
                                           stare <= stop;
                                    else 
                                            stare <= shiftareA;
                                    end if;  
            when stop =>
                                    t <= '1';
                                    loada <= '0';
                                    loadb <= '0';
                                    loadq <= '0';
                                    shla <= '0';
                                    shlq <= '0';
                                    subb <=  '0';
                                    selmuxin <= '0';
            when others => stare <= idle;          
            
            
         end case;             
                
                
    end if;
end process;
term <= t;




end Behavioral;
