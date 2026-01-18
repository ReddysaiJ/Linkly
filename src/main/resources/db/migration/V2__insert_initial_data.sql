
INSERT INTO users (email, password, name, role)
VALUES
  ('admin@gmail.com',    'admin',   'Administrator', 'ROLE_ADMIN'),
  ('reddysai2107@gmail.com', 'secret', 'Reddysai',      'ROLE_USER');

INSERT INTO short_urls (short_key, original_url, created_by, created_at, expires_at, is_private, click_count)
VALUES
  ('rs1Aed', 'https://openai.com/blog/chatgpt',           1, '2024-07-15'::timestamptz, NULL, FALSE, 0),
  ('hujfDf', 'https://huggingface.co/models',             1, '2024-07-16'::timestamptz, NULL, FALSE, 0),
  ('ertcbn', 'https://ollama.com/',                       1, '2024-07-17'::timestamptz, NULL, FALSE, 0),
  ('edfrtg', 'https://github.com/langchain-ai/langchain', 1, '2024-07-18'::timestamptz, NULL, TRUE,  0),
  ('vbgtyh', 'https://www.anthropic.com/',                1, '2024-07-19'::timestamptz, NULL, FALSE, 0),
  ('rtyfgb', 'https://vectorhub.org/',                    1, '2024-07-25'::timestamptz, NULL, FALSE, 0),
  ('rtvbop', 'https://github.com/mistralai/mistral-src',  1, '2024-07-26'::timestamptz, NULL, FALSE, 0),
  ('2wedfg', 'https://www.langchain4j.dev/',              1, '2024-07-27'::timestamptz, NULL, TRUE,  0),
  ('6yfrd4', 'https://pinecone.io/',                      1, '2024-07-28'::timestamptz, NULL, FALSE, 0),
  ('r5t4tt', 'https://platform.openai.com/docs/guides/gpt',1, '2024-07-29'::timestamptz, NULL, FALSE, 0),
  ('ffr4rt', 'https://deepmind.google/technologies/gemini/',1,'2024-08-10'::timestamptz, NULL, FALSE, 0),
  ('9oui7u', 'https://github.com/facebookresearch/llama', 1, '2024-08-11'::timestamptz, NULL, FALSE, 0),
  ('cvbg5t', 'https://github.com/huggingface/transformers',1,'2024-08-12'::timestamptz, NULL, FALSE, 0),
  ('nm6ytf', 'https://ollama.com/blog/open-webui',         1, '2024-08-13'::timestamptz, NULL, FALSE, 0),
  ('tt5y6r', 'https://github.com/flowiseai/Flowise',      1, '2024-08-14'::timestamptz, NULL, FALSE, 0),
  ('fgghty', 'https://www.pinecone.io/learn/vector-database/',1,'2024-08-15'::timestamptz,NULL,FALSE,0),
  ('f45tre', 'https://www.trychroma.com/',                1, '2024-08-16'::timestamptz, NULL, FALSE, 0),
  ('54rt54', 'https://docs.langchain.com/docs/integrations/vectorstores/chroma',1,'2024-08-17'::timestamptz,NULL,FALSE,0);
