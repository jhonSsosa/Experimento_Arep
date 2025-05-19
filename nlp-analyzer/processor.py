import spacy

nlp = spacy.load("es_core_news_md")  # Usa el modelo en español

def analyze_paragraph(text: str):
    doc = nlp(text)
    keywords = [token.text for token in doc if token.pos_ in ("NOUN", "VERB") and not token.is_stop]
    num_words = len([token for token in doc if not token.is_punct])
    num_sentences = len(list(doc.sents))

    return {
        "num_words": num_words,
        "num_sentences": num_sentences,
        "keywords": keywords[:10]
    }
