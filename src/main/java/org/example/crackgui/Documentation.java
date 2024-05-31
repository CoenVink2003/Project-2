package org.example.crackgui;

import java.util.List;

public class Documentation {
    private List<FoundDocumentation> foundDocumentation;

    public List<FoundDocumentation> getFoundDocumentation() {
        return foundDocumentation;
    }

    public void setFoundDocumentation(List<FoundDocumentation> foundDocumentation) {
        this.foundDocumentation = foundDocumentation;
    }

    static class FoundDocumentation {
        private List<String> keywords;
        private Object documentation;

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public Object getDocumentation() {
            return documentation;
        }

        public void setDocumentation(Object documentation) {
            this.documentation = documentation;
        }
    }
}