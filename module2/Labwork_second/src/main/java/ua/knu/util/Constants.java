package ua.knu.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException(Other.UTILITY_CLASS);
    }

    public static class Student {
        private Student() {
            throw new IllegalStateException(Other.UTILITY_CLASS);
        }

        public static final String NEWS_ENTITY = "news";
        public static final String NEWS = "news";
        public static final String NEWS_TITLE = "newsTitle";

        public static final String NEWS_ID = "newsId";
        public static final String TITLE = "title";
        public static final String TEXT = "text";

        public static final String UPDATED_TITLE = "updatedTitle";
        public static final String UPDATED_TEXT = "updatedText";
        public static final String UPDATED_CATEGORY_ID = "updatedCategoryId";
    }

    public static class Group {
        private Group() {
            throw new IllegalStateException(Other.UTILITY_CLASS);
        }

        public static final String NEWS_CATEGORY_ENTITY = "newsCategory";
        public static final String CATEGORIES = "categories";
        public static final String CATEGORIES_TITLE = "categoriesTitle";

        public static final String CATEGORY_ID = "categoryId";
        public static final String CATEGORY_NAME = "categoryName";
        public static final String AGE_LIMIT = "ageLimit";
        public static final String UPDATED_CATEGORY_NAME = "updatedCategoryName";
        public static final String UPDATED_CATEGORY_AGE_LIMIT = "updatedCategoryAgeLimit";
    }

    public static class Other {
        private Other() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String ENTITY = "entity";
        public static final String UTILITY_CLASS = "Utility class!";
        public static final String INDEX = "/index.jsp";
    }
}
