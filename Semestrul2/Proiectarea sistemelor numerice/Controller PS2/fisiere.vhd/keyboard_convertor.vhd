library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;


entity keyboard_control is
	port(
	clk25: in std_logic;
	clr:in std_logic;
	PS2C:in std_logic;
	PS2D:in std_logic;
	key1: out std_logic_vector(7 downto 0);
	key2: out std_logic_vector(7 downto 0);
	key3: out std_logic_vector(7 downto 0)
	);
end keyboard_control; 


architecture keyboard_control of keyboard_control is

type state_type is ( start, astept_clk_lo_1, astept_clk_hi_1, iau_key_1,	
astept_clk_lo_2, astept_clk_hi_2, iau_key_2, key_iesire,
astept_clk_lo_3, astept_clk_hi_3, iau_key_3); 
signal state: state_type;
signal ps2c_f,ps2d_f:std_logic;
signal ps2c_filtru,ps2d_filtru:std_logic_vector(7 downto 0); 	
signal registru1, registru2, registru3: std_logic_vector(10 downto 0);
signal valoare1, valoare2, valoare3: std_logic_vector(7 downto 0);
signal bit_counter: std_logic_vector(3 downto 0);
constant counter_max: std_logic_vector(3 downto 0) := "1011";
--am atribuit valoarea maxima de biti care poate fi transmisa 
begin
 
-- filtru pentu date si clock care vin serial de la tastatura  

     filtru:process(clk25,clr)
	 begin
		 if clr = '1' then 
			 
			 ps2c_filtru <= (others => '0');
			 ps2d_filtru <= (others => '0');
			 ps2c_f <= '1';
			 ps2d_f <= '1';
			 
		 elsif clk25'event and clk25 = '1' then
			 
			 ps2c_filtru(7) <= PS2C;
			 ps2c_filtru(6 downto 0) <= ps2c_filtru(7 downto 1);
			 ps2d_filtru(7) <= PS2D;
			 ps2d_filtru(6 downto 0) <= ps2d_filtru(7 downto 1); 
			 
			 if ps2c_filtru = "11111111" then
				 ps2c_f <= '1';	
			 elsif ps2c_filtru = "00000000" then
				 ps2c_f <= '0';	  
			 end if; 
			 
			 if ps2d_filtru = "11111111" then
				 ps2d_f <= '1';	
			 elsif ps2d_filtru = "00000000" then
				 ps2d_f <= '0';	  
			 end if; 
			 
		    end if;	
		end process filtru;	   
	
	
	-- initializam pentru a citi o tasta
	
	status_key: process(clk25,clr)
	begin
		if clr = '1' then
			state <= start;
			bit_counter <= (others => '0');
			registru1 <= (others => '0');
			registru2 <= (others => '0');
			registru3 <= (others => '0');
			valoare1 <= (others => '0');
			valoare2 <= (others => '0');
			valoare3 <= (others => '0');
		elsif clk25'event and clk25 = '1' then
		 case state is  
				
			when start =>
				if ps2d_f = '1' then 
					state <= start;
				else 
					state <= astept_clk_lo_1;
				end if;
				
			when astept_clk_lo_1 =>
				if bit_counter < counter_max then
					if ps2c_f = '1' then
					state <= astept_clk_lo_1;
				else
					state <= astept_clk_hi_1;
					registru1 <= ps2d_f & registru1(10 downto 1);
				end if;	 
				else 
					state <= iau_key_1;
				end if;	
				
			 when astept_clk_hi_1 =>
			      if ps2c_f = '0' then 
				 	state <= astept_clk_hi_1;
				  else
					 state <= astept_clk_lo_1;
					 bit_counter <= bit_counter + 1;
				  end if;
				  
			  when iau_key_1 => 
			        valoare1 <= registru1(8 downto 1);
			  	   bit_counter <= (others => '0');
				   state <= astept_clk_lo_2;
				   
			  when astept_clk_lo_2 =>
				if bit_counter < counter_max then
					if ps2c_f = '1' then
					state <= astept_clk_lo_2;
				else
					state <= astept_clk_hi_2;
					registru2 <= ps2d_f & registru2(10 downto 1);
				end if;	 
				else 
					state <= iau_key_2;
				end if;	
				
			   when astept_clk_hi_2 =>
			      if ps2c_f = '0' then 
				 	state <= astept_clk_hi_2;
				  else
					 state <= astept_clk_lo_2;
					 bit_counter <= bit_counter + 1;
				  end if;	  
				  
				when iau_key_2 => 
			        valoare2 <= registru2(8 downto 1);
			  	    bit_counter <= (others => '0');
				    state <= key_iesire;  
				  
				
				when key_iesire =>
				if valoare2 = "11110000" then 
					state <= astept_clk_lo_3;
					if valoare2 = "11100000" then
						state <= astept_clk_lo_1;
					else
						state <= astept_clk_lo_2;
					end if;
				 end if;   
				 
				 when astept_clk_lo_3 =>
				 if bit_counter < counter_max then 
					 if ps2c_f = '1' then 
						 state <= astept_clk_lo_3;
					 else
						 state <= astept_clk_hi_3;
						 registru3 <= ps2d_f & registru3(10 downto 1);
					 end if;
					 else 
						 state <= iau_key_3;
					 end if;
					 
				  	 
					when astept_clk_hi_3 =>
					if ps2c_f = '0' then 
						state <= astept_clk_hi_3;
					else 
						state <= astept_clk_lo_3;
						bit_counter <= bit_counter + 1;
					end if;
					
					when iau_key_3 =>
					valoare3 <= registru3(8 downto 1);
					bit_counter <= (others => '0');
					state <= astept_clk_lo_1; 
					
				end case;
				
			end if;
		end process status_key;
		
		key1 <= valoare1;
		key2 <= valoare2;
		key3 <= valoare3;
end keyboard_control;							