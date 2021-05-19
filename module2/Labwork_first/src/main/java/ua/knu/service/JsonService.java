package ua.knu.service;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import ua.knu.model.News;
import ua.knu.model.NewsAgency;
import ua.knu.model.NewsCategory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Optional;

@Getter
@FieldDefaults(makeFinal = true)
public class JsonService {

    private NewsAgency newsAgency;
    private File storage;

    @SneakyThrows
    public JsonService() {
        this.storage = new File("src/main/resources/news.json");

        val json = FileUtils.readFileToString(storage, Charset.defaultCharset());
        this.newsAgency = JSON.parseObject(json, NewsAgency.class);
    }

    public void addCategory(NewsCategory newsCategory) {
        newsAgency.getNewsCategories().add(newsCategory);
    }

    public void updateCategory(String newsCategory, NewsCategory updatedNewsCategory) {
        val optionalCategory = getCategory(newsCategory);

        if (optionalCategory.isPresent()) {
            val category = optionalCategory.get();

            if (!StringUtils.isEmpty(updatedNewsCategory.getName())) category.setName(updatedNewsCategory.getName());
            if (updatedNewsCategory.getAgeLimit() != null) category.setAgeLimit(updatedNewsCategory.getAgeLimit());
        }
    }

    public void removeCategory(String category) {
        val optionalNewsCategory = getCategory(category);

        optionalNewsCategory.ifPresent(newsCategory -> newsAgency.getNewsCategories().remove(newsCategory));
    }

    public void addNews(String category, News news) {
        val optionalNewsCategory = getCategory(category);

        optionalNewsCategory.ifPresent(newsCategory -> newsCategory.getNews().add(news));
    }

    public void updateNews(String category, News oldNews, News updatedNews) {
        val optionalNewsCategory = getCategory(category);

        if (optionalNewsCategory.isPresent()) {
            val newsCategory = optionalNewsCategory.get();

            val optionalNews = getNews(newsCategory, oldNews);

            if (optionalNews.isPresent()) {
                val student = optionalNews.get();

                if (!StringUtils.isEmpty(updatedNews.getTitle()))
                    student.setTitle(updatedNews.getTitle());

                if (!StringUtils.isEmpty(updatedNews.getAuthor()))
                    student.setAuthor(updatedNews.getAuthor());
            }
        }
    }

    public void removeNews(String category, News news) {
        val optionalNewsCategory = getCategory(category);

        optionalNewsCategory.ifPresent(newsCategory -> newsCategory.getNews().remove(news));
    }

    @SneakyThrows
    public void save() {
        FileUtils.writeStringToFile(storage, JSON.toJSONString(newsAgency), Charset.defaultCharset(), false);
    }

    private Optional<News> getNews(NewsCategory newsCategory, News newsToFind) {
        return newsCategory.getNews()
                .stream()
                .filter(news -> news.equals(newsToFind))
                .findFirst();
    }

    private Optional<NewsCategory> getCategory(String categoryName) {
        return newsAgency.getNewsCategories()
                .stream()
                .filter(newsCategory -> newsCategory.getName().equals(categoryName))
                .findFirst();
    }
}
