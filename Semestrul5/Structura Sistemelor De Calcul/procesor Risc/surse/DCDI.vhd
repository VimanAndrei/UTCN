----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 12/15/2021 06:20:35 PM
-- Design Name: 
-- Module Name: DCDI - Behavioral
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

entity DCDI is
  Port (
  Instr: in std_logic_vector(31 downto 0);
  RegWr: out std_logic;
  AdrD: out std_logic_vector(3 downto 0);
  MxD: out std_logic_vector(1 downto 0);
  SSalt: out std_logic_vector(1 downto 0);
  CSalt: out std_logic;
  MemWr: out std_logic;
  OpUAL: out std_logic_vector(3 downto 0);
  MxA: out std_logic;
  MxB: out std_logic;
  AdrSA: out std_logic_vector(3 downto 0);
  AdrSB: out std_logic_vector(3 downto 0); 
  SelC: out std_logic
   );
end DCDI;

architecture Behavioral of DCDI is

begin
AdrD <= Instr(23 downto 20);
AdrSA <= Instr(19 downto 16);  
AdrSB <= Instr(15 downto 12);

control: process(Instr)
begin
case Instr(31 downto 24) is
 when "00000000" => --NOP
                    OpUAL <= "0000"; 
					RegWr <= '0';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					                  
 when "01000000" => --MOVA
                    OpUAL <= "0000"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					
 when "00000010" => --ADD
                    OpUAL <= "0010"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					
 when "00000101" => --SUB
                    OpUAL <= "0101"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
 when "00001000" => --AND
                    OpUAL <= "1000"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					
 when "00001001" => --OR
                    OpUAL <= "1001"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					
 when "00001010" => --XOR
                    OpUAL <= "1010"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0'; 
					
 when "00001011" => --NOT
                    OpUAL <= "1011"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0';
					 
 when "00100010" => --ADDI
                    OpUAL <= "0010"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '1';
 when "00100101" => --SUBI
                    OpUAL <= "0101"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '1';
					
 when "00101000" => --ANDI
                    OpUAL <= "1000"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '0';
 when "00101001" => --ORI
                    OpUAL <= "1001"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '0';
 
 when "00101010" => --XORI
                    OpUAL <= "1010"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '0';
 when "00001101" => --SHR
                    OpUAL <= "1101"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0';
 when "00001110" => --SHL
                    OpUAL <= "1110"; 
					RegWr <= '1';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0';
 when "01100000" => --BZ
                    OpUAL <= "0000"; 
					RegWr <= '0';
					MxD <= "00";
					SSalt <= "01";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '1';
 when "01010000" => --BNZ
                    OpUAL <= "0000"; 
					RegWr <= '0';
					MxD <= "00";
					SSalt <= "01";
					CSalt <= '1';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '1';
					SelC <= '1';
 when "01101001" => --HALT 
                    OpUAL <= "0000"; 
					RegWr <= '0';
					MxD <= "00";
					SSalt <= "11";
					CSalt <= '1';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0';
when others =>
                    OpUAL <= "0000"; 
					RegWr <= '0';
					MxD <= "00";
					SSalt <= "00";
					CSalt <= '0';
					MemWr <= '0';
					MxA <= '0';
					MxB <= '0';
					SelC <= '0';
end case; 
end process control;

end Behavioral;
