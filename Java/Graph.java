package Java;
import java.util.List;

//Creates a visual representation of stock values
class Graph implements IRetrieveGraph{
    private IRetrieveData dataRetrieved;

    //Gets the data from the database
    public Graph(IRetrieveData dataRetrieved){
        this.dataRetrieved = dataRetrieved;
    }

    //Prepares the graph from the data provided
    public void getGraphData(List<IGetStocks> stocksList){
        System.out.println("Stocks: ");

        for (IGetStocks stock : stocksList){
            System.out.println("----------------------------------------------------------------------------\n");
            System.out.println("Name: " + stock.getStockNames() + ", Value: "+ stock.getStockValues() + "\n");
            
        }
        System.out.println("----------------------------------------------------------------------------\n");
    }

    //Writes the graph
    public void visualiseData(){
        List<IGetStocks> stocksList = dataRetrieved.getStocksList();
        getGraphData(stocksList);
    }

}