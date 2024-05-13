package sad.User.Class;

import java.util.ArrayList;
import java.util.List;

import sad.User.Interface.IStockOptions;

public class StockOptions implements IStockOptions {

    private List<Stock> ownedStocks = new ArrayList<>();

    @Override
    public void buyStock(Stock company, int quantity) {
        for (int i = 0; i < quantity; i++) {
            ownedStocks.add(company);
        }
    }

    @Override
    public void sellStock() {
        if (!ownedStocks.isEmpty()) {
            ownedStocks.remove(0);
        } else {
            System.out.println("No stocks to sell.");
        }
    }
}
