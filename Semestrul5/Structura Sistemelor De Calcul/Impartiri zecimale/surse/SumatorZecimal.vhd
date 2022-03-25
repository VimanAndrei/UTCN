----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 11/01/2021 02:24:09 PM
-- Design Name: 
-- Module Name: SumatorZecimal - Behavioral
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

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SumatorZecimal is
 Port ( X : in STD_LOGIC_VECTOR (3 downto 0);
           Y : in STD_LOGIC_VECTOR (3 downto 0);
           Cin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (3 downto 0);
           Cout : out STD_LOGIC);
end SumatorZecimal;

architecture Behavioral of SumatorZecimal is
signal sum,sum1 : std_logic_vector(3 downto 0) := "0000";
signal c,cc,ccc: std_logic:='0';
begin
dut1: entity WORK.sumator4biti port map(x,y,cin,sum,c);
ccc <= c or (sum(3) and sum(2)) or (sum(3) and sum(1));
sum1 <= '0'& ccc & ccc & '0';

dut2: entity WORK.sumator4biti port map(sum1,sum,'0',s,cc);
cout<=ccc;


end Behavioral;
