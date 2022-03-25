----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 11/14/2021 06:30:37 PM
-- Design Name: 
-- Module Name: impartireaCuRefacere - Behavioral
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

entity impartireaCuRefacere is
generic(n : natural:=16);
  Port ( 
         clk: in std_logic;
         rst : in STD_LOGIC;
         start : in STD_LOGIC;
         x : in STD_LOGIC_VECTOR(n-1 downto 0);
         y : in STD_LOGIC_VECTOR(n-1 downto 0);
         a : out STD_LOGIC_VECTOR(n-1 downto 0);
         q : out STD_LOGIC_VECTOR(n-1 downto 0);
         Term : out STD_LOGIC);
end impartireaCuRefacere;

architecture Behavioral of impartireaCuRefacere is


signal loada,loadb,loadq,shla,shlq,subb,ovf,selmuxin:  std_logic :='0';
signal an,outsuman: std_logic:='1';
signal outb,insum,outsum,outq: std_logic_vector(n-1 downto 0);
signal outa,ina: std_logic_vector(n downto 0);
signal nn: std_logic_vector(3 downto 0):=(others =>'0'); 

begin

unitateDeControl: entity work.unitateaDeControl_impartireCuRefacere generic map ( n => n) port map (
            start=>start,clk=>clk,rst=>rst,
            an=>an,selmuxin=>selmuxin,loada=>loada,loadb=>loadb,
            loadq=>loadq,shla=>shla,shlq=>shlq,subb=>subb,
            term=>term,nn=>nn);

registrulb: entity work.nbit_register generic map(n => n) port map(
    d=>y,
    ce=>loadb,
    clk=>clk,
    rst=>rst,
    q=>outb);

 
insum <= x"9999" - outB when subb = '1' else outB;

suma: entity WORK.sumator_16biti
                port map( 
                x=>outa(n-1 downto 0),
                y=>insum,
                cin=>subb,
                s=>outsum, 
                cout=>outsuman);
                
an<=outsum(n-1);             
ina <= '0' & x"0000" when selmuxin = '1' else an & outsum;

registrula: entity WORK.slrn_registru_de_deplasare_stanga 
       generic map(n => n+1) port map( 
                d => ina,
                sri=> outq(n-1 downto n-4),
                load=>loada,
                ce=>shla,
                clk=>clk,
                rst=>rst,
                q=>outa);
                

registrulq: entity WORK.slrn_registru_de_deplasare_stanga 
       generic map(n => n) port map( 
                d => x,
                sri=> nn,
                load=>loadq,
                ce=>shlq,
                clk=>clk,
                rst=>rst,
                q=>outq);                

q<=outq;
a<= outa(n-1 downto 0);  



end Behavioral;
