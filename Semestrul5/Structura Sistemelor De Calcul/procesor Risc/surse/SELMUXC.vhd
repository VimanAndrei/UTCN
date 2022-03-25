----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 12/15/2021 05:44:42 PM
-- Design Name: 
-- Module Name: SELMUXC - Behavioral
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

entity SELMUXC is
  Port ( 
  SSalt: in std_logic_vector(1 downto 0);
  CSalt: in std_logic;
  Z: in std_logic;
  MxC: out std_logic_vector(1 downto 0)  
  );
end SELMUXC;

architecture Behavioral of SELMUXC is

begin
MxC <="00" when (SSalt = "00")  or (SSalt = "01" and CSalt = '0' and Z = '0') or (SSalt = "01" and CSalt = '1' and Z = '1') else
      "01" when (SSalt = "01" and CSalt = '0' and Z = '1') or (SSalt = "01" and CSalt = '1' and Z = '0') or (SSalt = "11" and CSalt = '0') else 
      "10" when (SSalt = "10") else 
      "11" when (SSalt = "11" and CSalt = '1');


end Behavioral;
