import java.util.*;

class Blackjack{

    
    
public static void main(String[] args){
      
     System.out.println("Blackjack!!!");
     Deck playingDeck = new Deck();
     playingDeck.createFullDeck();
     playingDeck.shuffle();

     //create a deck for the player
     Deck playerDeck = new Deck();
     Deck playerSideDeck1 = new Deck();
     Deck playerSideDeck2 = new Deck();
     Deck playerTempDeck= new Deck();
     Deck dealerDeck = new Deck();

     double playerMoney = 100.00;

     Scanner userIn = new Scanner(System.in);

     //game loop

     while(playerMoney > 0){
        //bet
        System.out.println("Toplam paraniz "+ playerMoney+ " toplam bahsinizi giriniz");
        double playerBet = userIn.nextDouble();
        if(playerBet> playerMoney){
            System.out.println("sahip olmadiginiz parayi koyamazsin");
            break;
        }

        boolean endRound = false;
        
        int splitround = 0;


        playerDeck.draw(playingDeck);
        dealerDeck.draw(playingDeck);
        
        playerDeck.draw(playingDeck);
        dealerDeck.draw(playingDeck);

        while(true){
            if(splitround==0){
            System.out.println("Eliniz:");
            System.out.println(playerDeck.toString());
            System.out.println("Elinizin toplami:"+ playerDeck.cardsValue());
            
            System.out.println("Dealerin eli: "+ dealerDeck.getCard(0).toString()+ " ve [Gizli]");
            System.out.println("Hit:1 , Stay:2 , Split:3, Double-Down:4");
            }
            
       
            // player ne yapacak
            
        
            int response = userIn.nextInt();

            if(response==1){
                playerDeck.draw(playingDeck);
                System.out.println("Sunu cektiniz:"+ playerDeck.getCard(playerDeck.deckSize()-1).toString());
                if((playerDeck.cardsValue() >21)&&splitround==0){
                    System.out.println("Busted! Eliniz"+ playerDeck.cardsValue());
                    playerMoney -= playerBet;      
                    endRound = true;
                    break;    
                }   
            }
            if(response==2){
                break;
            }
            if(response==3){
                splitround=1;
                playerDeck.moveAllToDeck(playerTempDeck);
                playerSideDeck1.addCard(playerTempDeck.getCard(0));
                playerSideDeck1.draw(playingDeck);
                playerSideDeck2.addCard(playerTempDeck.getCard(1));
                playerSideDeck2.draw(playingDeck);
                
                System.out.println("birinci eliniz: "+playerSideDeck1+"\n");
                System.out.println("ikinci eliniz: "+playerSideDeck2+"\n");
                boolean splitround1=true;
                boolean splitround2=false;
                System.out.println("Birinci eliniz icin hit:1 or stay:2");
                int cevab = userIn.nextInt();
                
                if(cevab==1){
                while(splitround1==true){
                
                    
                    playerSideDeck1.draw(playingDeck);
                    if((playerSideDeck1.cardsValue()>21)&&endRound==false){
                        System.out.println("Birinci el busted;");
                        System.out.println("Ikinci eliniz icin hit:1 or stay:2");
                        splitround1=false;
                        splitround2=true;
                        break;
                    }                  
                    
                    System.out.println("birinci elinizin son hali: "+ playerSideDeck1.cardsValue());
                    System.out.println("Hit:1 or Stay:2");
                    int cevabb=userIn.nextInt();
                    if(cevabb==2){
                        System.out.println("ikinci eliniz icin hit:1 or stay:2");
                        
                        splitround1=false;
                        splitround2=true;
                    
                    }
                    else{
                        playerSideDeck1.draw(playingDeck);
                        if(playerSideDeck1.cardsValue()>21){
                            System.out.println("birinci el busted");
                            System.out.println("ikinci eliniz icin hit:1 or stay:2");
                            splitround1=false;
                            splitround2=true;
                            break;
                            
                        }
                    }
                }
                
            }
            else if(cevab==2){
                splitround1=false;
                
                System.out.println("ikinci eliniz icin hit:1 or stay:2");
            while(splitround1==false&&splitround2==true){

                playerSideDeck2.draw(playingDeck);
                
                System.out.println("ikinci elinizin son hali: "+ playerSideDeck2.cardsValue());
                if((playerSideDeck2.cardsValue()>21)&&endRound==false){
                    System.out.println("Ikinci el busted");
                    System.out.println("ikinci elinizin son hali: "+ playerSideDeck2.cardsValue());
                    
                    splitround=0;
                    splitround2=false;
                    endRound=true;
                }
                
                    System.out.println("Hit:1 or Stay:2");
                    int cevap=userIn.nextInt();
                    if(cevap==2){
                        splitround=0;
                        splitround2=false;
                        endRound=true;
                    }
                    else{
                        playerSideDeck2.draw(playingDeck);
                        System.out.println("ikinci elinizin son hali: "+ playerSideDeck2.cardsValue());
                        if(playerSideDeck2.cardsValue()>21){
                            System.out.println("ikinci el busted");
                            
                            splitround=0;
                            splitround2=false;
                            endRound=true;
                        }
                    }
            }

        }
                
            }
        }
        //Dealer 2. kartı aciyor
        System.out.println("Dealerin eli"+dealerDeck.toString());
        if((dealerDeck.cardsValue() > playerDeck.cardsValue())&& endRound== false&&splitround==0){
            System.out.println("Dealer KAZANDI");
            playerMoney -= playerBet;
            endRound = true;
        }
        while((dealerDeck.cardsValue()<17)&& endRound==false){
            dealerDeck.draw(playingDeck);
            System.out.println("Dealer:"+ dealerDeck.getCard(dealerDeck.deckSize()-1).toString()+"'i cekti");
        }
        System.out.println("Dealerin eli:"+dealerDeck.cardsValue());
        if((dealerDeck.cardsValue()>21)&& endRound==false){
            if(splitround==1){
                if(playerSideDeck1.cardsValue()<=21&&playerSideDeck2.cardsValue()<=21){
                    System.out.println("iki el de kazandi");
                    endRound=true;
                }
                if(playerSideDeck1.cardsValue()>21&&playerSideDeck2.cardsValue()<21){
                    System.out.println("birinci el kayip ikinci el kazandi");
                    endRound=true;
                }
                if(playerSideDeck1.cardsValue()<21&&playerSideDeck2.cardsValue()>21){
                    System.out.println("birinci el kazandi ikinci el kayip");
                    endRound=true;
                }
                if(playerSideDeck1.cardsValue()>21&&playerSideDeck2.cardsValue()>21){
                    System.out.println("iki el de busted");
                    endRound=true;
                }
            }
            System.out.println("dealer busted, kazandın");
            playerMoney +=playerBet;
            endRound= true;
        }

        if((playerDeck.cardsValue()==dealerDeck.cardsValue())&& endRound==false){
            System.out.println("push");
            endRound= true;
        }
        if((playerDeck.cardsValue()>dealerDeck.cardsValue())&& endRound==false){
            System.out.println("Kazandın");
            playerMoney+=playerBet;
            endRound= true;
        }
        
        if((playerSideDeck1.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
            System.out.println("Birinci elin push");
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci elin push");
                endRound=true;
            }
            else if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                endRound=true;
            }
            else if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                endRound=true;
            }

        }
        if((playerSideDeck1.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
            System.out.println("birinci elin kaybetti");
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound){
                System.out.println("ikinci elin push");
                endRound=true;
            }
            else if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                endRound=true;
            }
            else if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                endRound= true;
            }
        }
        if((playerSideDeck1.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
            System.out.println("birinci el kazandi");
            if((playerSideDeck2.cardsValue()==dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el push");
                endRound=true;
            }
            else if((playerSideDeck2.cardsValue()<dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kayip");
                endRound= true;
            }
            else if((playerSideDeck2.cardsValue()>dealerDeck.cardsValue())&&endRound==false&&playerSideDeck1.cardsValue()<=21){
                System.out.println("ikinci el kazandi");
                endRound=true;
            }

        }
        

        playerDeck.moveAllToDeck(playingDeck);
        dealerDeck.moveAllToDeck(playingDeck);
        System.out.println("Elin sonu");
     }
     System.out.println("bitik oc öldün:DD");
    


}
}


//* 
//if((playerDeck.getCard(0)).getValue()==playerDeck.getCard(1).getValue()){
//    playerSideDeck1.addCard(playerDeck.getCard(0));
//    playerSideDeck1.draw(playingDeck);
//    playerSideDeck2.addCard(playerDeck.getCard(1));
//    playerSideDeck2.draw(playingDeck);
//    System.out.println("Birinci eliniz:"+playerSideDeck1.cardsValue()+"Ikinci eliniz:"+ playerSideDeck2);
//    System.out.println("Birinci eliniz icin Hit:1 or Stay:2");
//    if(response==1){
//       playerSideDeck1.draw(playingDeck);
//       System.out.println("Sunu cektiniz:"+ playerSideDeck1.getCard(playerSideDeck1.deckSize()-1).toString());
//    }
//    if(response==2){
//       System.out.println("ikinci eliniz icin Hit:1 or Stay:2");
//       if(response==1){
//           playerSideDeck2.draw(playingDeck);
//           System.out.println("Sunu cektiniz:"+ playerSideDeck2.getCard(playerSideDeck2.deckSize()-1).toString());
//
//       }
//       if(response==2){
//           break;
//       }
//    }
//}
//else{
//   System.out.println("Eliniz ayni degere sahip degil");
//   
//}