package com.viniciusjanner.desafio.testing.core.domain.model

import com.viniciusjanner.desafio.core.domain.model.Event

class EventFactory {

    sealed class EventFake {
        data object Event1 : EventFake()
        data object Event2 : EventFake()
        data object Event3 : EventFake()
        data object Event4 : EventFake()
        data object Event5 : EventFake()
    }

    fun create(eventFake: EventFake): Event =
        when (eventFake) {
            EventFake.Event1 -> {
                Event(
                    people = null,
                    date = 1534784400000,
                    description = "O Patas Dadas estará na Redenção, nesse domingo, com cães para adoção e produtos à venda!\n\nNa ocasião, teremos bottons, bloquinhos e camisetas!\n\nTraga seu Pet, os amigos e o chima, e venha aproveitar esse dia de sol com a gente e com alguns de nossos peludinhos - que estarão prontinhos para ganhar o ♥ de um humano bem legal pra chamar de seu. \n\nAceitaremos todos os tipos de doação:\n- guias e coleiras em bom estado\n- ração (as que mais precisamos no momento são sênior e filhote)\n- roupinhas \n- cobertas \n- remédios dentro do prazo de validade",
                    image = "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png",
                    longitude = -51.2146267,
                    latitude = -30.0392981,
                    price = 29.99,
                    title = "Feira de adoção de animais na Redenção",
                    id = "1"
                )
            }
            EventFake.Event2 -> {
                Event(
                    people = null,
                    date = 1534784400000,
                    description = "Vamos ajudar !!\n\nSe você tem na sua casa roupas que estão em bom estado de uso e não sabemos que fazer, coloque aqui na nossa página sua cidade e sua doação, com certeza poderá ajudar outras pessoas que estão passando por problemas econômicos no momento!!\n\nAjudar não faz mal a ninguém!!!\n",
                    image = "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg",
                    longitude = -51.2148497,
                    latitude = -30.037878,
                    price = 59.99,
                    title = "Doação de roupas",
                    id = "2"
                )
            }
            EventFake.Event3 -> {
                Event(
                    people = null,
                    date = 1534784400000,
                    description = "Atenção! Para nosso brique ser o mais organizado possível, leia as regras e cumpra-as:\n* Ao publicar seus livros, evite criar álbuns (não há necessidade de remetê-los a nenhum álbum);\n* A publicação deverá conter o valor desejado;\n* É preferível publicar uma foto do livro em questão a fim de mostrar o estado em que se encontra;\n* Respeite a ordem da fila;\n* Horário e local de encontro devem ser combinados inbox;\n* Caso não possa comparecer, avise seu comprador/vendedor previamente;\n* Caso seu comprador desista, comente o post com \"disponível\";\n* Não se esqueça de apagar a publicação se o livro já foi vendido, ou ao menos comente \"vendido\" para que as administradoras possam apagá-la;\n* Evite discussões e respeite o próximo;\n",
                    image = "http://www.fernaogaivota.com.br/documents/10179/1665610/feira-troca-de-livros.jpg",
                    longitude = -51.2148497,
                    latitude = -30.037878,
                    price = 19.99,
                    title = "Feira de Troca de Livros",
                    id = "3"
                )
            }
            EventFake.Event4 -> {
                Event(
                    people = null,
                    date = 1534784400000,
                    description = "Toda quarta-feira, das 17h às 22h, encontre a feira mais charmosa de produtos frescos, naturais e orgânicos no estacionamento do Shopping. Sintonizado com a tendência crescente de busca pela alimentação saudável, consumo consciente e qualidade de vida. \n\nAs barracas terão grande variedade de produtos, como o shiitake cultivado em Ibiporã há mais de 20 anos, um sucesso na mesa dos que não abrem mão do saudável cogumelo na dieta. Ou os laticínios orgânicos da Estância Baobá, famosos pelo qualidade e modo de fabricação sustentável na vizinha Jaguapitã. Também estarão na feira as conhecidas compotas e patês tradicionais da Pousada Marabú, de Rolândia.\n\nA feira do Catuaí é uma nova opção de compras de produtos que não são facilmente encontrados no varejo tradicional, além de ótima pedida para o descanso de final de tarde em família e entre amigos. E com o diferencial de ser noturna, facilitando a vida dos consumidores que poderão sair do trabalho e ir direto para a “Vila Verde”, onde será possível degustar delícias saudáveis nos bistrôs, ouvir música ao vivo, levar as crianças para a diversão em uma estação de brinquedos e relaxar ao ar livre.\n\nEXPOSITORES DA VILA VERDE CATUAÍ\n\nCraft Hamburgueria\nNido Pastíficio\nSabor e Saúde\nTerra Planta\nEmpório da Papinha\nEmpório Sabor da Serra\nBoleria Dom Leonardi\nCoisas que te ajudam a viver\nPatês da Marisa\nMarabú\nBaobá\nAkko\nCervejaria Amadeus\n12 Tribos\nParr Kitchen\nHorta Fazenda São Virgílio\nHorta Chácara Santo Antonio\nSur Empanadas\nFit & Sweet\nSK e T Cogumelos\nDos Quintana\n\nLocal: Estacionamento (entrada principal do Catuaí Shopping Londrina)\n\n\nAcesso à Feira gratuito.",
                    image = "https://i2.wp.com/assentopublico.com.br/wp-content/uploads/2017/07/Feira-de-alimentos-org%C3%A2nicos-naturais-e-artesanais-ganha-um-novo-espa%C3%A7o-em-Ribeir%C3%A3o.jpg",
                    longitude = -51.2148497,
                    latitude = -30.037878,
                    price = 19.0,
                    title = "Feira de Produtos Naturais e Orgânicos",
                    id = "4"
                )
            }
            EventFake.Event5 -> {
                Event(
                    people = null,
                    date = 1534784400000,
                    description = "Uma maratona de programação, na qual estudantes e profissionais das áreas de DESIGN, PROGRAMAÇÃO e MARKETING se reunirão para criar projetos com impacto social positivo através dos pilares de Educação Financeira e Colaborar para Transformar.\n\nO evento será realizado por duas empresas que são movidas pela transformação: o Woop Sicredi e a Smile Flame.\n\n// Pra ficar esperto:\n\n- 31/08, 01 e 02 de Setembro, na PUCRS;\n- 34 horas de duração;\n- Atividades direcionadas para criação de soluções digitais de impacto social;\n- Mentorias para apoiar o desenvolvimento das soluções;\n- Conteúdo de apoio; \n- Alimentação inclusa;\n\n// Programação\n\nSexta-feira - 31/08 - 19h ás 22h\nSábado e Domingo - 01 e 02/09 - 9h do dia 01/09 até as 18h do dia 02/09.\n\n// Realização\nWoop Sicredi\nSmile Flame\n\nMaiores infos em: https://www.hackathonsocial.com.br/\nTá com dúvida? Manda um e-mail pra gabriel@smileflame.com\n\nEaí, ta tão animado quanto nós? Let´s hack!",
                    image = "https://static.wixstatic.com/media/579ac9_81e9766eaa2741a284e7a7f729429022~mv2.png",
                    longitude = -51.2148497,
                    latitude = -30.037878,
                    price = 59.99,
                    title = "Hackathon Social Woop Sicredi",
                    id = "5"
                )
            }
        }
}
