# hero game- Dynamic Programming & Greedy Approach
 
In this project, a game was designed using dynamic programming, random approach and 
greedy approach algorithms.

• Players start with a predetermined amount of gold. 

• There will be many options (different hero) for each piece type.

• Spend your gold on pieces. Higher level does not mean higher value –
select based on Gold and Attack.

• Maximize total attack points of your pieces.

Players start with a predetermined amount of gold. 

• Spend your gold on pieces by selecting the set of hero. 

• Maximize total attack points of your pieces. 

• You don’t have to fill all levels. You cannot select two hero at the same level.

Trial #1
User → Dynamic programming
Computer → Greedy approach



Trial #2
User → Dynamic programming
Computer → Randomized approach

![Ekran görüntüsü 2023-03-08 162151](https://user-images.githubusercontent.com/74409269/223820433-48e980ec-9cc8-460c-a576-8b29e209b69d.jpg)

![Ekran görüntüsü 2023-03-08 162210](https://user-images.githubusercontent.com/74409269/223820851-b7745c86-9724-43df-b370-1c5366e6d4dc.jpg)

Analysis: 

•Dynamic Programming Approach:
This algorithm is designed based on the Knapsack algorithm, which is a widely known 
algorithm. Two matrices are used in this algorithm. The first matrix K[][] contains elements 
of the integer data type, the second matrix H[][] contains objects from the HeroSet class. In 
the K matrix, the rows represent the gold value of the available heroes, and the columns 
represent the total gold_amount we have. In the H matrix, a list of which heroes are in the 
corresponding place in the K matrix is kept simultaneously. The newly evaluated hero's score 
and the point we get when we subtract that hero's price from our gold amount If the total 
value of the hero we can get with provides a higher value than the old value we have, this 
value is valid for our current position. . If not, the value in the top row is valid. However, in 
cases where the new value is valid, the type of the new hero needs to be checked.
The maxHero() function is used for this. In the maxHero() function, the following operations 
are performed; 
If a new value is obtained by adding it to the old value, then the HeroSet of the old value 
should be examined. If the type of the new hero is available in our old heroset list, then a 
comparison is made between the attack points of the two same type heroes. If the new hero 
has a higher score, the other hero is deleted from the old HeroSet and a new hero is added to 
the HeroSet list. Simultaneously, the deleted hero's value is also deleted from the K matrix 
and updated with the new hero's value. Thus, we get the maximum attack points we can get in 
the last element of the K matrix, and a heroSet object containing the heroes in this army in the 
last element of the H matrix.

•Greedy Approach
In the greedy approach, the heroes in our heroes list are sorted by their gold / attack_points 
ratio. Our first data in the leaderboard was the hero that brought the most ATK per gold. 
Heroes were added to the army according to this ordered list. However, since the repetition of 
the same hero type is avoided, it is necessary to compare the previously added hero types in 
each operation. In this algorithm, this requirement is met with String operations. A string 
variable was kept and each time a new hero was added to the army, the hero type was added 
to this variable. This variable provides controls with the contains() function from the String 
class. If the new hero's type and containing function are correct, the same action is taken by 
moving to the next hero until the value is wrong.

•Random Approach
In the random approach algorithm, heroes have been added to the army as much as our gold is 
sufficient and without repetition of type. The string operation used in the greedy approach is 
used for non-duplication control.

Result:
At level 7, when the amount of stones in each level is given as 10 and the amount of gold is 
150, the working times of these three approaches are measured in milliseconds and the results 
are as follows;
Dynamic Programming Approach= 6 ms
Greedy Approach= 22 ms
Random Approach = 1 ms
As seen in the results, dynamic programming is faster as it always proceeds using the data 
from the previous step, sorting operation is done before starting the greedy algorithm, so the 
run time is much longer.
