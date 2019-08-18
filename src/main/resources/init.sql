DROP TRIGGER IF EXISTS notMinus ON users;
DROP FUNCTION IF EXISTS notMinus;

DROP TABLE IF EXISTS user_checkout_table CASCADE ;
DROP TABLE IF EXISTS checkout CASCADE ;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS items CASCADE ;
DROP TABLE IF EXISTS orders CASCADE ;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(25) NOT NULL,
    country VARCHAR(20) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(30) NOT NULL,
    zip_code VARCHAR(8) NOT NULL,
    money SMALLINT NOT NULL,
    status BOOLEAN DEFAULT FALSE,
	CONSTRAINT email_not_empty CHECK (email <> ''),
	CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE checkout (
    checkout_id SERIAL,
    PRIMARY KEY (checkout_id),
    book_title VARCHAR(30) NOT NULL,
    buyer VARCHAR(25) NOT NULL,
    price SMALLINT NOT NULL

);

CREATE TABLE orders (
    order_id SERIAL,
    book_title VARCHAR(30) NOT NULL,
    buyer VARCHAR(25) NOT NULL,
    price SMALLINT NOT NULL
);

CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    author VARCHAR(25) NOT NULL,
    url TEXT DEFAULT 'https://i.pinimg.com/originals/d6/fc/4f/d6fc4fc1f32a80941f2ee84c1ab6bcea.jpg',
    plot TEXT NOT NULL,
    price SMALLINT NOT NULL
);

CREATE TABLE user_checkout_table (
    user_id INTEGER,
    checkout_id INTEGER,
    PRIMARY KEY (user_id, checkout_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (checkout_id) REFERENCES checkout(checkout_id)
);

INSERT INTO checkout (book_title, buyer, price) VALUES
    ('Gintama', 'user1@user1', 10),
    ('Gantz', 'user1@user1', 18);

INSERT INTO orders (book_title, buyer, price) VALUES
('Dr. Stone', 'user1@user1', 10),
('Gantz', 'user1@user1', 20);

INSERT INTO users (email, password, name, country, city, street, zip_code, money, status) VALUES
	('user1@user1', 'user1', 'Denes', 'Hungary', 'Miskolc', 'Fun st.', 2144, 20, FALSE),
    ('user2@user2', 'admin', 'admin', 'Hell', 'Nightmare', 'Elm st.', 6699, 9999, TRUE),
    ('user3@user3', 'user3', 'Batman', 'USA', 'Gotham', '1007 Mountain Drive', '214K4', 900, FALSE);

INSERT INTO user_checkout_table (user_id, checkout_id) VALUES
    (1, 1),
    (1, 2);

INSERT INTO items (title, author, url, plot, price) VALUES
	('Dr. Stone', 'Riichiro Inagaki', 'https://i.redd.it/dqpph2imkoe31.jpg', 'One fateful day, all of humanity was petrified by a blinding flash of light. After several millennia, high schooler Taiju awakens and finds himself lost in a world of statues. However, he’s not alone! His science-loving friend Senku’s been up and running for a few months and he''s got a grand plan in mind—to kickstart civilization with the power of science!', 10),
    ('Gantz', 'Hiroya Oku', 'http://comics.panini.it/store/media/catalog/product/cache/80/small_image/9df78eab33525d08d6e5fb8d27136e95/M/M/MMASN074_0.jpg', 'Thought your life was bad? Sometimes, death is worse. There is no salvation, peace, nor god waiting to receive you into their care. But wait, a god? Maybe you are talking about that big black ball stuck in the room with you. Now you are thrown into a game, fighting green aliens and robot monsters for the chance to survive.

When Kei Kurono is killed, he thus finds himself caught in such a game—a test of his skills, morals, and will to survive. His life is not his own; his death is spat and trampled upon over and over again. What happens if he does not listen? God knows.

A word of warning: Gantz is not for the faint-hearted, but neither is it as simple as it looks. Gore, rape, and violence is rampant, as are portrayals of greed, violence, and all the ugliness that one sees in society today.', 15),
    ('Gintama', 'Hideaki Sorachi', 'https://pbs.twimg.com/media/CaOs6PWUEAAj6_u.jpg', 'With even more dirty jokes, tongue-in-cheek parodies, and shameless references, Gintama'' follows the Yorozuya team through more of their misadventures in the vibrant, alien-filled world of Edo.', 20),
    ('Hajime no Ippo', 'Morikawa Jouji', 'https://www.barraques.cat/pngfile/big/21-216853_hajime-no-ippo-manga-cover.jpg', 'Makunouchi Ippo has been bullied his entire life. Constantly running errands and being beaten up by his classmates, Ippo has always dreamed of changing himself, but never has the passion to act upon it. One day, in the midst of yet another bullying, Ippo is saved by Takamura Mamoru, who happens to be a boxer. Ippo faints from his injuries and is brought to the Kamogawa boxing gym to recover. As he regains consciousness, he is awed and amazed at his new surroundings in the gym, though lacks confidence to attempt anything. Takamura places a photo of Ippo''s classmate on a punching bag and forces him to punch it. It is only then that Ippo feels something stir inside him and eventually asks Takamura to train him in boxing. Thinking that Ippo does not have what it takes, Takamura gives him a task deemed impossible and gives him a one week time limit. With a sudden desire to get stronger, for himself and his hard working mother, Ippo trains relentlessly to accomplish the task within the time limit. Thus Ippo''s journey to the top of the boxing world begins.', 15),
    ('No Game No Life', 'Kamiya Yuu', 'https://static.zerochan.net/No.Game.No.Life.full.2274535.jpg', 'No Game No Life is a surreal comedy that follows Sora and Shiro, shut-in NEET siblings and the online gamer duo behind the legendary username "Blank." They view the real world as just another lousy game; however, a strange e-mail challenging them to a chess match changes everything—the brother and sister are plunged into an otherworldly realm where they meet Tet, the God of Games.

The mysterious god welcomes Sora and Shiro to Disboard, a world where all forms of conflict—from petty squabbles to the fate of whole countries—are settled not through war, but by way of high-stake games. This system works thanks to a fundamental rule wherein each party must wager something they deem to be of equal value to the other party''s wager. In this strange land where the very idea of humanity is reduced to child''s play, the indifferent genius gamer duo of Sora and Shiro have finally found a real reason to keep playing games: to unite the sixteen races of Disboard, defeat Tet, and become the gods of this new, gaming-is-everything world.', 10),
    ('Boku dake ga Inai Machi', 'Taku Kishimoto', 'https://www.canalbd.net/img/couvpage/18/9782355929182_cg.jpg', 'When tragedy is about to strike, Satoru Fujinuma finds himself sent back several minutes before the accident occurs. The detached, 29-year-old manga artist has taken advantage of this powerful yet mysterious phenomenon, which he calls "Revival," to save many lives.

However, when he is wrongfully accused of murdering someone close to him, Satoru is sent back to the past once again, but this time to 1988, 18 years in the past. Soon, he realizes that the murder may be connected to the abduction and killing of one of his classmates, the solitary and mysterious Kayo Hinazuki, that took place when he was a child. This is his chance to make things right.

Boku dake ga Inai Machi follows Satoru in his mission to uncover what truly transpired 18 years ago and prevent the death of his classmate while protecting those he cares about in the present.', 30),
    ('The Breaker', 'Jeon Geuk-Jin', 'https://www.bdfugue.com/media/catalog/product/cache/1/image/400x/17f82f742ffe127f42dca9de82fb58b1/9/7/9782820900586_1_75.jpg', 'Yi "Shioon" Shi-Woon''s everyday life at Nine Dragons High School—which consists of beatings from fellow student Ho Chang and his gang—is far from ideal. But one day, a mysterious man named Han Chun Woo spots one of these beatings and instead of offering support, brands Shioon a coward for refusing to fight back, adding insult to injury. To Shioon''s surprise, he finds out that Chun Woo is the new substitute English teacher at his school.

Tired of the daily abuse, Shioon decides to enroll at a martial arts academy to learn how to defend himself. On the way there, he stumbles upon Chun Woo in a predicament—cornered in an alley by a group of angry men! Provoked, Chun Woo suddenly dispatches them using martial arts techniques, which Shioon covertly records. Later, he uses this recording to blackmail Chun Woo into teaching him to defend himself. Reluctantly, Chun Woo agrees, and Shioon is soon thrust into the world of martial arts, known as Murim. However, Shioon is naive and unaware of his master''s shady past and the unseen underbelly of society. How will Chun Woo manage to teach Shioon and help him survive in the world of Murim?', 15);

CREATE OR REPLACE FUNCTION notMinus() RETURNS TRIGGER AS '
    BEGIN
        IF NEW.money < 0 THEN
            RAISE ''You re out of money'';
        END IF;
        RETURN NULL;
    END
' LANGUAGE PLPGSQL;

CREATE TRIGGER notMinus
    AFTER UPDATE OR INSERT ON users
    FOR EACH ROW EXECUTE PROCEDURE notMinus();
