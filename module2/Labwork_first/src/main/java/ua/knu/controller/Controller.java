package ua.knu.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import ua.knu.model.News;
import ua.knu.model.NewsCategory;
import ua.knu.service.JsonService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private TreeView<String> mTreeView;

    @FXML
    private TextField mNewsTitle;
    @FXML
    private TextField mNewsAuthor;
    @FXML
    private TextField mCategoryName;
    @FXML
    private TextField mNewsCategoryAgeLimit;

    private JsonService jsonService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.jsonService = new JsonService();

        printTreeView();
    }

    public void addNews() {
        String categoryName = getCategoryName(getSelectedTreeItem());

        val newsTitle = mNewsTitle.getText();
        val newsText = mNewsAuthor.getText();

        if (StringUtils.isEmpty(mNewsTitle.getText()) || StringUtils.isEmpty(newsText)) {
            throw new IllegalArgumentException("title and news author are required");
        }

        jsonService.addNews(categoryName, new News(newsTitle, newsText));
        printTreeView();
    }

    public void updateNews() {
        val selectedTreeItem = getSelectedTreeItem();
        val categoryName = getCategoryName(selectedTreeItem.getParent());

        val split = Arrays.stream(selectedTreeItem.getValue().split(" -"))
                .filter(element -> !StringUtils.isEmpty(element))
                .collect(Collectors.toList());
        val oldNewsTitle = split.get(0);
        val oldNewsText = split.get(1);

        val newNewsTitle = mNewsTitle.getText();
        val newNewsText = mNewsAuthor.getText();

        jsonService.updateNews(categoryName, new News(oldNewsTitle, oldNewsText), new News(newNewsTitle, newNewsText));
        printTreeView();
    }

    public void removeNews() {
        val selectedTreeItem = getSelectedTreeItem();
        val categoryName = getCategoryName(selectedTreeItem.getParent());

        val split = selectedTreeItem.getValue().split(" -");
        val newsTitle = split[0];
        val newsText = split[1];

        jsonService.removeNews(categoryName, new News(newsTitle, newsText));
        printTreeView();
    }

    public void addNewsCategory() {
        val categoryName = mCategoryName.getText();
        val newsCategoryAgeLimit = Integer.parseInt(mNewsCategoryAgeLimit.getText());

        jsonService.addCategory(new NewsCategory(categoryName, newsCategoryAgeLimit, new ArrayList<>()));
        printTreeView();
    }

    public void removeNewsCategory() {
        val categoryName = getCategoryName(getSelectedTreeItem());

        jsonService.removeCategory(categoryName);
        printTreeView();
    }

    public void updateNewsCategory() {
        val categoryName = getCategoryName(getSelectedTreeItem());
        val updatedCategoryName = mCategoryName.getText();
        val updatedNewsCategoryAgeLimit = !StringUtils.isEmpty(mNewsCategoryAgeLimit.getText()) ? Integer.parseInt(mNewsCategoryAgeLimit.getText()) : null;

        jsonService.updateCategory(categoryName, new NewsCategory(updatedCategoryName, updatedNewsCategoryAgeLimit, new ArrayList<>()));
        printTreeView();
    }

    public void save() {
        jsonService.save();
    }

    private String getCategoryName(TreeItem<String> treeItem) {
        return treeItem.getValue().split(StringUtils.SPACE)[1];
    }

    private TreeItem<String> getSelectedTreeItem() {
        val selectedItem = mTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            throw new IllegalArgumentException("No item selected");
        }

        return selectedItem;
    }

    private void printTreeView() {
        mTreeView.setRoot(null);
        val groups = jsonService.getNewsAgency().getNewsCategories();

        TreeItem<String> root = new TreeItem<>("Agency");

        for (val group : groups) {
            val categoryInfo = String.format("Category: %s %s+", group.getName(), group.getAgeLimit());
            val news = group.getNews();
            val groupTreeItem = new TreeItem<>(categoryInfo);

            for (val iterable : news) {
                val newsInfo = String.format("%s - %s", iterable.getTitle(), iterable.getAuthor());
                groupTreeItem.getChildren().add(new TreeItem<>(newsInfo));
            }

            root.getChildren().add(groupTreeItem);
        }

        mTreeView.setRoot(root);
    }
}
