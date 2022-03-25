----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 11/01/2021 01:37:31 PM
-- Design Name: 
-- Module Name: sumatorZecimalElementar - Behavioral
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

entity sumator4biti is
    Port ( X : in STD_LOGIC_VECTOR (3 downto 0);
           Y : in STD_LOGIC_VECTOR (3 downto 0);
           Cin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (3 downto 0);
           Cout : out STD_LOGIC);
end sumator4biti;

architecture Behavioral of sumator4biti is
signal carry: std_logic_vector(3 downto 0):= "0000";
begin
GEN_ADD: for i in 0 to 3 generate
    LOWER_BIT: if I=0 generate    
      U0: entity WORK.sumator1bit port map(
         x(i),y(i),cin,s(i),carry(i));
    end generate LOWER_BIT;

    UPPER_BITS: if I > 0 generate
      UX: entity WORK.sumator1bit port map(
         x(I),y(I),carry(i-1),s(i),carry(i));
    end generate UPPER_BITS;
    
end generate GEN_ADD;

Cout<= Carry(3);
end Behavioral;
