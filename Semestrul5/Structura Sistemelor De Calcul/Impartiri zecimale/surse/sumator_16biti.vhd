----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 11/22/2021 08:47:02 PM
-- Design Name: 
-- Module Name: sumator_16biti - Behavioral
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

entity sumator_16biti is
Port ( X : in STD_LOGIC_VECTOR (15 downto 0);
           Y : in STD_LOGIC_VECTOR (15 downto 0);
           Cin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (15 downto 0);
           Cout : out STD_LOGIC);
end sumator_16biti;

architecture Behavioral of sumator_16biti is
signal carry: std_logic_vector(3 downto 0):="0000";
signal sum : std_logic_vector(15 downto 0):=x"0000";

begin
dut1: entity WORK.SumatorZecimal port map(x=>x(3 downto 0),y=>y(3 downto 0),
cin=>cin,s=>sum(3 downto 0),cout=>carry(0));

dut2: entity WORK.SumatorZecimal port map(x=>x(7 downto 4),y=>y(7 downto 4),
cin=>carry(0),s=>sum(7 downto 4),cout=>carry(1));

dut3: entity WORK.SumatorZecimal port map(x=>x(11 downto 8),y=>y(11 downto 8),
cin=>carry(1),s=>sum(11 downto 8),cout=>carry(2));

dut4: entity WORK.SumatorZecimal port map(x=>x(15 downto 12),y=>y(15 downto 12),
cin=>carry(2),s=>sum(15 downto 12),cout=>carry(3));
s<=sum;
cout<=carry(3);


end Behavioral;
